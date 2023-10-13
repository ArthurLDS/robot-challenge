package com.example.robot_game_play.data

import com.example.robot_game_play.domain.models.Player
import com.example.robot_game_play.domain.models.MoveDirection
import com.example.robot_game_play.domain.repository.GameRepository
import com.example.robot_game_play.presentation.theme.PlayerBlue
import com.example.robot_game_play.presentation.theme.PlayerBlueHead
import com.example.robot_game_play.presentation.theme.PlayerGreen
import com.example.robot_game_play.presentation.theme.PlayerGreenHead
import com.example.robot_game_play.presentation.theme.PlayerOrange
import com.example.robot_game_play.presentation.theme.PlayerOrangeHead
import com.example.robot_game_play.presentation.theme.PlayerPink
import com.example.robot_game_play.presentation.theme.PlayerPinkHead

class GameRepositoryImpl : GameRepository {

    override fun getPlayers(quantity: Int, boardSize: Int): List<Player> = listOf(
        Player(
            body = listOf(Pair(0, boardSize - 1)),
            number = 0,
            currentDirection = MoveDirection.RIGHT,
            bodyColor = PlayerPink,
            headColor = PlayerPinkHead,
            score = 0
        ),
        Player(
            body = listOf(Pair(boardSize - 1, 0)),
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
        ),
        Player(
            body = listOf(Pair(boardSize - 1, boardSize - 1)),
            number = 3,
            currentDirection = MoveDirection.RIGHT,
            bodyColor = PlayerOrange,
            headColor = PlayerOrangeHead,
            score = 0
        ),
    ).take(quantity)

    override fun getPlayersQuantity(): Int = 4

    override fun getBoardSize(): Int = 7

}