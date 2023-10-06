package com.example.robot_game_play.domain.game

import com.example.robot_game_play.domain.models.Player
import com.example.robot_game_play.domain.models.GameState
import com.example.robot_game_play.domain.models.MoveDirection

class GameEngine {
    private var numPlayers: Int = 0
    private var boardSize: Int = 0

    fun initGameState(
        playerList: List<Player>,
        numPlayers: Int,
        boardSize: Int
    ): GameState {
        this.numPlayers = numPlayers
        this.boardSize = boardSize

        val playerFoodLocation = initPlayerFood()
        return GameState(
            foodCoord = playerFoodLocation,
            playerList = playerList,
            currentPlayerTurn = rand(numPlayers),
            stuckPlayers = 0,
            stalemateRounds = 0
        )
    }

    private fun initPlayerFood(): Pair<Int, Int> = randomCoord(cornerCoords)

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

    private fun updatePlayerTurn(currentTurn: Int): Int {
        return if (currentTurn + 1 > numPlayers - 1) 0
        else currentTurn + 1
    }

    private fun nextPlayerCoord(head: Pair<Int, Int>, direction: MoveDirection): Pair<Int, Int> =
        when (direction) {
            MoveDirection.UP -> Pair(head.first, head.second - 1)
            MoveDirection.DOWN -> Pair(head.first, head.second + 1)
            MoveDirection.RIGHT -> Pair(head.first + 1, head.second)
            MoveDirection.LEFT -> Pair(head.first - 1, head.second)
        }

    private fun tryMovingHorizontal(
        validDirections: List<MoveDirection>,
        head: Int,
        food: Int
    ): MoveDirection? = when {
        head > food && validDirections.contains(MoveDirection.LEFT) -> MoveDirection.LEFT
        validDirections.contains(MoveDirection.RIGHT) -> MoveDirection.RIGHT
        else -> null
    }

    private fun tryMovingVertical(
        validDirections: List<MoveDirection>,
        head: Int,
        food: Int
    ): MoveDirection? = when {
        head > food && validDirections.contains(MoveDirection.UP) -> MoveDirection.UP
        validDirections.contains(MoveDirection.DOWN) -> MoveDirection.DOWN
        else -> null
    }

    private fun calculateValidDirections(
        head: Pair<Int, Int>,
        playerList: List<Player>
    ): List<MoveDirection?> {
        val validDirections = mutableListOf<MoveDirection>()

        if (isValidDirection(nextPlayerCoord(head, MoveDirection.UP), playerList))
            validDirections.add(MoveDirection.UP)

        if (isValidDirection(nextPlayerCoord(head, MoveDirection.DOWN), playerList))
            validDirections.add(MoveDirection.DOWN)

        if (isValidDirection(nextPlayerCoord(head, MoveDirection.LEFT), playerList))
            validDirections.add(MoveDirection.LEFT)

        if (isValidDirection(nextPlayerCoord(head, MoveDirection.RIGHT), playerList))
            validDirections.add(MoveDirection.RIGHT)

        return validDirections
    }

    private fun isValidDirection(coord: Pair<Int, Int>, playerList: List<Player>): Boolean {
        return when {
            // check top & bottom boundaries
            coord.first < 0 || coord.first > boardSize - 1 -> false
            // check left & right boundaries
            coord.second < 0 || coord.second > boardSize - 1 -> false
            // check for body
            else -> playerList.none { it.body.contains(coord) }
        }
    }

    private val cornerCoords = listOf(
        Pair(boardSize - 1, 0),
        Pair(0, boardSize - 1),
        Pair(0, 0),
        Pair(boardSize - 1, boardSize - 1)
    )

    private fun calculateOptimalMovementBasic(
        head: Pair<Int, Int>,
        playerList: List<Player>,
        foodCoord: Pair<Int, Int>
    ): MoveDirection? {
        // check if stuck, early return
        var validDirections = calculateValidDirections(head, playerList)

        if (validDirections.isEmpty()) return null
        else if (validDirections.size == 1) return validDirections.first()
        // Note: we know this cast is OK b/c we early returned the empty list!
        else validDirections = validDirections as List<MoveDirection>

        // you need to move towards the correct x & y
        moveTowardsFood(head, foodCoord, validDirections)?.let { return it }

        // try to move optimally
        movePlayerOptimally(head, foodCoord, validDirections)?.let { return it }

        // just try to move!
        moveRandomly(head, foodCoord, validDirections)?.let { return it }

        // Worst case, pick one at random lol
        return validDirections[rand(validDirections.size)]
    }

    private fun movePlayerOptimally(
        head: Pair<Int, Int>,
        food: Pair<Int, Int>,
        validDirections: List<MoveDirection>
    ): MoveDirection? {
        val xDistanceToFood = head.first - food.first
        val yDistanceToFood = head.second - food.second

        var directionToMove: MoveDirection? = null

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
        validDirections: List<MoveDirection>
    ): MoveDirection? {
        var directionToMove: MoveDirection? = null
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
        validDirections: List<MoveDirection>
    ): MoveDirection? {
        var directionToMove: MoveDirection? = null
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

    private fun movePlayer(
        playerList: List<Player>,
        currentPlayerTurn: Int,
        foodCoord: Pair<Int, Int>
    ): Pair<List<Player>, Pair<Int, Int>?> {

        var currentPlayer = playerList[currentPlayerTurn]
        val moveDirection = calculateOptimalMovementBasic(currentPlayer.body.first(), playerList, foodCoord)
        val newPlayerList = playerList.toMutableList()

        val newPlayer = if (moveDirection != null) {
            createNewPlayer(currentPlayer, moveDirection)
        } else {
            currentPlayer.copy(currentDirection = null)
        }
        newPlayerList[currentPlayerTurn] = newPlayer

        return if (checkFoodEaten(newPlayerList[currentPlayerTurn], foodCoord)) {
            onFoodEaten(newPlayerList, currentPlayerTurn)
        } else {
            Pair(newPlayerList, null)
        }
    }

    private fun createNewPlayer(currentPlayer: Player, moveDirection: MoveDirection): Player {
        val newPlayerBody = currentPlayer.body.toMutableList()
        val newHead = nextPlayerCoord(currentPlayer.body.first(), moveDirection)
        newPlayerBody.add(0, newHead)
        return currentPlayer.copy(
            body = newPlayerBody,
            currentDirection = moveDirection
        )
    }

    private fun checkFoodEaten(currentPlayer: Player, foodCoord: Pair<Int, Int>): Boolean {
        return (currentPlayer.body.first() == foodCoord)
    }

    private fun onFoodEaten(
        playerList: MutableList<Player>,
        currentPlayerTurn: Int
    ): Pair<List<Player>, Pair<Int, Int>> {
        val newFoodCoord = randomCoord()
        for (player in playerList) {
            if (player.number == currentPlayerTurn) {
                player.score++
            }
            player.body = listOf(randomCoord(listOf(newFoodCoord)))
            player.currentDirection = MoveDirection.RIGHT
        }

        return Pair(playerList, newFoodCoord)
    }

    private fun updateNotStuckPlayer(
        gameState: GameState,
        playerList: List<Player>,
        currentPlayerTurn: Int,
        currentFoodCoord: Pair<Int, Int>
    ): GameState {
        val playerList = playerList
        val newPlayerList = movePlayer(playerList, currentPlayerTurn, currentFoodCoord)
        val nextTurn =
            if (newPlayerList.second != null) rand(numPlayers) else updatePlayerTurn(currentPlayerTurn)

        val stuckPlayers = if (newPlayerList.second != null) 0 else gameState.stuckPlayers

        return gameState.copy(
            currentPlayerTurn = nextTurn,
            playerList = newPlayerList.first,
            foodCoord = newPlayerList.second ?: currentFoodCoord,
            stuckPlayers = stuckPlayers
        )
    }

    private fun updateStuckPlayer(
        gameState: GameState,
        playerList: List<Player>,
        currentPlayerTurn: Int
    ): GameState {
        // check if all players are stuck
        if (gameState.stuckPlayers == playerList.size) {
            val newPlayerList = onFoodEaten(playerList.toMutableList(), -1)
            val stalemateCount = gameState.stalemateRounds + 1
            return gameState.copy(
                currentPlayerTurn = rand(numPlayers),
                playerList = newPlayerList.first,
                foodCoord = newPlayerList.second,
                stuckPlayers = 0,
                stalemateRounds = stalemateCount
            )

        } else {
            val stuckPlayers = gameState.stuckPlayers + 1
            return gameState.copy(
                currentPlayerTurn = updatePlayerTurn(currentPlayerTurn),
                stuckPlayers = stuckPlayers
            )
        }
    }

    fun playTurn(state: GameState): GameState {
        with(state) {
            val currentPlayerTurn = currentPlayerTurn
            val currentPlayer = playerList[currentPlayerTurn]
            val currentFoodCoord = foodCoord

            // Check if the player is stuck
            if (currentPlayer.currentDirection != null) {
                return updateNotStuckPlayer(state, playerList, currentPlayerTurn, currentFoodCoord)
            } else {
                // check if all players are stuck
                return updateStuckPlayer(state, playerList, currentPlayerTurn)
            }
        }
    }
}