package com.example.robot_game_play.domain.game

import com.example.robot_game_play.domain.models.Character
import com.example.robot_game_play.domain.models.GameState
import com.example.robot_game_play.domain.models.CharacterDirection

class GameEngine {
    private var numCharacters: Int = 0
    private var boardSize: Int = 0

    fun initGameState(
        characterList: List<Character>,
        numCharacters: Int,
        boardSize: Int
    ): GameState {
        this.numCharacters = numCharacters
        this.boardSize = boardSize

        val characterFoodLocation = initCharacterFood()
        return GameState(
            foodCoord = characterFoodLocation,
            characterList = characterList,
            currentCharacterTurn = rand(numCharacters),
            stuckCharacters = 0,
            stalemateRounds = 0
        )
    }

    private fun initCharacterFood(): Pair<Int, Int> = randomCoord()

    private fun randomCoord(exclude: List<Pair<Int, Int>>? = null): Pair<Int, Int> {
        val coord = Pair(
            rand(boardSize),
            rand(boardSize)
        )
        return if (exclude != null && exclude.contains(coord) && exclude.size != (boardSize * boardSize)) {
            randomCoord(exclude)
        } else {
            coord
        }
    }

    private fun updateCharacterTurn(currentTurn: Int): Int {
        return if (currentTurn + 1 > numCharacters - 1) 0
        else currentTurn + 1
    }

    private fun nextCharacterCoord(head: Pair<Int, Int>, direction: CharacterDirection): Pair<Int, Int> =
        when (direction) {
            CharacterDirection.UP -> Pair(head.first, head.second - 1)
            CharacterDirection.DOWN -> Pair(head.first, head.second + 1)
            CharacterDirection.RIGHT -> Pair(head.first + 1, head.second)
            CharacterDirection.LEFT -> Pair(head.first - 1, head.second)
        }

    private fun tryMovingHorizontal(
        validDirections: List<CharacterDirection>,
        head: Int,
        food: Int
    ): CharacterDirection? = when {
        head > food && validDirections.contains(CharacterDirection.LEFT) -> CharacterDirection.LEFT
        validDirections.contains(CharacterDirection.RIGHT) -> CharacterDirection.RIGHT
        else -> null
    }

    private fun tryMovingVertical(
        validDirections: List<CharacterDirection>,
        head: Int,
        food: Int
    ): CharacterDirection? = when {
        head > food && validDirections.contains(CharacterDirection.UP) -> CharacterDirection.UP
        validDirections.contains(CharacterDirection.DOWN) -> CharacterDirection.DOWN
        else -> null
    }

    private fun calculateValidDirections(
        head: Pair<Int, Int>,
        characterList: List<Character>
    ): List<CharacterDirection?> {
        val validDirections = mutableListOf<CharacterDirection>()

        if (isValidDirection(nextCharacterCoord(head, CharacterDirection.UP), characterList))
            validDirections.add(CharacterDirection.UP)

        if (isValidDirection(nextCharacterCoord(head, CharacterDirection.DOWN), characterList))
            validDirections.add(CharacterDirection.DOWN)

        if (isValidDirection(nextCharacterCoord(head, CharacterDirection.LEFT), characterList))
            validDirections.add(CharacterDirection.LEFT)

        if (isValidDirection(nextCharacterCoord(head, CharacterDirection.RIGHT), characterList))
            validDirections.add(CharacterDirection.RIGHT)

        return validDirections
    }

    private fun isValidDirection(coord: Pair<Int, Int>, characterList: List<Character>): Boolean {
        return when {
            // check top & bottom boundaries
            coord.first < 0 || coord.first > boardSize - 1 -> false
            // check left & right boundaries
            coord.second < 0 || coord.second > boardSize - 1 -> false
            // check for body
            else -> characterList.none { it.body.contains(coord) }
        }
    }

    private fun calculateOptimalMovementBasic(
        head: Pair<Int, Int>,
        characterList: List<Character>,
        foodCoord: Pair<Int, Int>
    ): CharacterDirection? {
        // check if stuck, early return
        var validDirections = calculateValidDirections(head, characterList)

        if (validDirections.isEmpty()) return null
        else if (validDirections.size == 1) return validDirections.first()
        // Note: we know this cast is OK b/c we early returned the empty list!
        else validDirections = validDirections as List<CharacterDirection>

        // you need to move towards the correct x & y
        moveTowardsFood(head, foodCoord, validDirections)?.let { return it }

        // try to move optimally
        moveCharacterOptimally(head, foodCoord, validDirections)?.let { return it }

        // just try to move!
        moveRandomly(head, foodCoord, validDirections)?.let { return it }

        // Worst case, pick one at random lol
        return validDirections[rand(validDirections.size)]
    }

    private fun moveCharacterOptimally(
        head: Pair<Int, Int>,
        food: Pair<Int, Int>,
        validDirections: List<CharacterDirection>
    ): CharacterDirection? {
        val xDistanceToFood = head.first - food.first
        val yDistanceToFood = head.second - food.second

        var directionToMove: CharacterDirection? = null

        if (kotlin.math.abs(xDistanceToFood) > kotlin.math.abs(yDistanceToFood)) {
            // try moving left or right first
            tryMovingHorizontal(validDirections, head.first, food.first)?.let {
                directionToMove = it
            }
        } else if (kotlin.math.abs(xDistanceToFood) < kotlin.math.abs(yDistanceToFood)) {
            // try moving up or down
            tryMovingVertical(validDirections, head.second, food.second)?.let {
                directionToMove = it
            }
        }
        return directionToMove
    }

    private fun moveRandomly(
        head: Pair<Int, Int>,
        food: Pair<Int, Int>,
        validDirections: List<CharacterDirection>
    ): CharacterDirection? {
        var directionToMove: CharacterDirection? = null
        if (rand(2) == 0) {
            // try horizontal, then vertical
            tryMovingHorizontal(validDirections, head.first, food.first)?.let {
                directionToMove = it
            }
            tryMovingVertical(validDirections, head.second, food.second)?.let {
                directionToMove = it
            }
        } else {
            // try vertical, then horizontal
            tryMovingVertical(validDirections, head.second, food.second)?.let {
                directionToMove = it
            }
            tryMovingHorizontal(validDirections, head.first, food.first)?.let {
                directionToMove = it
            }
        }
        return directionToMove
    }

    private fun moveTowardsFood(
        head: Pair<Int, Int>,
        food: Pair<Int, Int>,
        validDirections: List<CharacterDirection>
    ): CharacterDirection? {
        var directionToMove: CharacterDirection? = null
        // you're in the correct x plane! navigate up or down towards food
        if (head.first == food.first) {
            tryMovingVertical(validDirections, head.second, food.second)?.let {
                directionToMove = it
            }
        }
        // you're in the correct y plane! navigate left or right towards food
        if (head.second == food.second) {
            tryMovingHorizontal(validDirections, head.first, food.first)?.let {
                directionToMove = it
            }
        }
        return directionToMove
    }

    private fun moveCharacter(
        characterList: List<Character>,
        currentCharacterTurn: Int,
        foodCoord: Pair<Int, Int>
    ): Pair<List<Character>, Pair<Int, Int>?> {

        var currentCharacter = characterList[currentCharacterTurn]
        val moveDirection = calculateOptimalMovementBasic(currentCharacter.body.first(), characterList, foodCoord)
        val newCharacterList = characterList.toMutableList()

        val newCharacter = if (moveDirection != null) {
            createNewCharacter(currentCharacter, moveDirection)
        } else {
            currentCharacter.copy(currentDirection = null)
        }
        newCharacterList[currentCharacterTurn] = newCharacter

        return if (checkFoodEaten(newCharacterList[currentCharacterTurn], foodCoord)) {
            onFoodEaten(newCharacterList, currentCharacterTurn)
        } else {
            Pair(newCharacterList, null)
        }
    }

    private fun createNewCharacter(currentCharacter: Character, moveDirection: CharacterDirection): Character {
        val newCharacterBody = currentCharacter.body.toMutableList()
        val newHead = nextCharacterCoord(currentCharacter.body.first(), moveDirection)
        newCharacterBody.add(0, newHead)
        return currentCharacter.copy(
            body = newCharacterBody,
            currentDirection = moveDirection
        )
    }

    private fun checkFoodEaten(currentCharacter: Character, foodCoord: Pair<Int, Int>): Boolean {
        return (currentCharacter.body.first() == foodCoord)
    }

    private fun onFoodEaten(
        characterList: MutableList<Character>,
        currentCharacterTurn: Int
    ): Pair<List<Character>, Pair<Int, Int>> {
        val newFoodCoord = randomCoord()
        for (character in characterList) {
            if (character.number == currentCharacterTurn) {
                character.score++
            }
            character.body = listOf(randomCoord(listOf(newFoodCoord)))
            character.currentDirection = CharacterDirection.RIGHT
        }

        return Pair(characterList, newFoodCoord)
    }

    private fun updateNotStuckCharacter(
        gameState: GameState,
        characterList: List<Character>,
        currentCharacterTurn: Int,
        currentFoodCoord: Pair<Int, Int>
    ): GameState {
        val characterList = characterList
        val newCharacterList = moveCharacter(characterList, currentCharacterTurn, currentFoodCoord)
        val nextTurn =
            if (newCharacterList.second != null) rand(numCharacters) else updateCharacterTurn(currentCharacterTurn)

        val stuckCharacters = if (newCharacterList.second != null) 0 else gameState.stuckCharacters

        return gameState.copy(
            currentCharacterTurn = nextTurn,
            characterList = newCharacterList.first,
            foodCoord = newCharacterList.second ?: currentFoodCoord,
            stuckCharacters = stuckCharacters
        )
    }

    private fun updateStuckCharacter(
        gameState: GameState,
        characterList: List<Character>,
        currentCharacterTurn: Int
    ): GameState {
        // check if all characters are stuck
        if (gameState.stuckCharacters == characterList.size) {
            val newCharacterList = onFoodEaten(characterList.toMutableList(), -1)
            val stalemateCount = gameState.stalemateRounds + 1
            return gameState.copy(
                currentCharacterTurn = rand(numCharacters),
                characterList = newCharacterList.first,
                foodCoord = newCharacterList.second,
                stuckCharacters = 0,
                stalemateRounds = stalemateCount
            )

        } else {
            val stuckCharacters = gameState.stuckCharacters + 1
            return gameState.copy(
                currentCharacterTurn = updateCharacterTurn(currentCharacterTurn),
                stuckCharacters = stuckCharacters
            )
        }
    }

    fun playTurn(state: GameState): GameState {
        with(state) {
            val currentCharacterTurn = currentCharacterTurn
            val currentCharacter = characterList[currentCharacterTurn]
            val currentFoodCoord = foodCoord

            // Check if the character is stuck
            if (currentCharacter.currentDirection != null) {
                return updateNotStuckCharacter(state, characterList, currentCharacterTurn, currentFoodCoord)
            } else {
                // check if all characters are stuck
                return updateStuckCharacter(state, characterList, currentCharacterTurn)
            }
        }
    }
}