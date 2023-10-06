package com.example.robot_game_play.testData

import com.example.robot_game_play.domain.models.Character
import com.example.robot_game_play.domain.models.CharacterDirection
import com.example.robot_game_play.domain.models.GameState
import com.example.robot_game_play.presentation.theme.SnekBlue
import com.example.robot_game_play.presentation.theme.SnekBlueHead
import com.example.robot_game_play.presentation.theme.SnekGreen
import com.example.robot_game_play.presentation.theme.SnekGreenHead
import com.example.robot_game_play.presentation.theme.SnekPink
import com.example.robot_game_play.presentation.theme.SnekPinkHead

object TestData {

    val CHARACTER_LIST_OF_THREE_DATA = listOf(
        Character(
            body = listOf(Pair(0, 7 - 1)),
            number = 0,
            currentDirection = CharacterDirection.RIGHT,
            bodyColor = SnekPink,
            headColor = SnekPinkHead,
            score = 0
        ),
        Character(
            body = listOf(Pair(7 - 1, 0)),
            number = 1,
            currentDirection = CharacterDirection.RIGHT,
            bodyColor = SnekGreen,
            headColor = SnekGreenHead,
            score = 0
        ),
        Character(
            body = listOf(Pair(0, 0)),
            number = 2,
            currentDirection = CharacterDirection.RIGHT,
            bodyColor = SnekBlue,
            headColor = SnekBlueHead,
            score = 0
        )
    )

    val CHARACTER_LIST_DATA = listOf(
        Character(
            body = listOf(Pair(0, 7 - 1)),
            number = 0,
            currentDirection = CharacterDirection.RIGHT,
            bodyColor = SnekPink,
            headColor = SnekPinkHead,
            score = 0
        ),
        Character(
            body = listOf(Pair(7 - 1, 0)),
            number = 1,
            currentDirection = CharacterDirection.RIGHT,
            bodyColor = SnekGreen,
            headColor = SnekGreenHead,
            score = 0
        )
    )

    val GAME_STATE_DATA = GameState(
        foodCoord = Pair(1, 1),
        characterList = CHARACTER_LIST_DATA,
        currentCharacterTurn = 1,
        stuckCharacters = 0,
        stalemateRounds = 0
    )
}