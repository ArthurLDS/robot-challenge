package com.example.robot_game_play.testData

import com.example.robot_game_play.domain.models.Player
import com.example.robot_game_play.domain.models.MoveDirection
import com.example.robot_game_play.domain.models.GameState
import com.example.robot_game_play.presentation.theme.PlayerBlue
import com.example.robot_game_play.presentation.theme.PlayerBlueHead
import com.example.robot_game_play.presentation.theme.PlayerGreen
import com.example.robot_game_play.presentation.theme.PlayerGreenHead
import com.example.robot_game_play.presentation.theme.PlayerPink
import com.example.robot_game_play.presentation.theme.PlayerPinkHead

object TestData {

    val CHARACTER_LIST_OF_THREE_DATA = listOf(
        Player(
            body = listOf(Pair(0, 7 - 1)),
            number = 0,
            currentDirection = MoveDirection.RIGHT,
            bodyColor = PlayerPink,
            headColor = PlayerPinkHead,
            score = 0
        ),
        Player(
            body = listOf(Pair(7 - 1, 0)),
            number = 1,
            currentDirection = MoveDirection.RIGHT,
            bodyColor = PlayerGreen,
            headColor = PlayerGreenHead,
            score = 0
        ),
        Player(
            body = listOf(Pair(0, 0)),
            number = 2,
            currentDirection = MoveDirection.RIGHT,
            bodyColor = PlayerBlue,
            headColor = PlayerBlueHead,
            score = 0
        )
    )

    val CHARACTER_LIST_DATA = listOf(
        Player(
            body = listOf(Pair(0, 7 - 1)),
            number = 0,
            currentDirection = MoveDirection.RIGHT,
            bodyColor = PlayerPink,
            headColor = PlayerPinkHead,
            score = 0
        ),
        Player(
            body = listOf(Pair(7 - 1, 0)),
            number = 1,
            currentDirection = MoveDirection.RIGHT,
            bodyColor = PlayerGreen,
            headColor = PlayerGreenHead,
            score = 0
        )
    )

    val GAME_STATE_DATA = GameState(
        foodCoord = Pair(1, 1),
        playerList = CHARACTER_LIST_DATA,
        currentPlayerTurn = 1,
        stuckPlayers = 0,
        stalemateRounds = 0
    )
}