package com.example.robot_game_play.data

import com.example.robot_game_play.domain.models.Character
import com.example.robot_game_play.domain.models.CharacterDirection
import com.example.robot_game_play.domain.repository.GameRepository
import com.example.robot_game_play.presentation.theme.SnekBlue
import com.example.robot_game_play.presentation.theme.SnekBlueHead
import com.example.robot_game_play.presentation.theme.SnekGreen
import com.example.robot_game_play.presentation.theme.SnekGreenHead
import com.example.robot_game_play.presentation.theme.SnekOrange
import com.example.robot_game_play.presentation.theme.SnekOrangeHead
import com.example.robot_game_play.presentation.theme.SnekPink
import com.example.robot_game_play.presentation.theme.SnekPinkHead

class GameRepositoryImpl : GameRepository {

    override fun getCharacters(quantity: Int, boardSize: Int): List<Character> = listOf(
        Character(
            body = listOf(Pair(0, boardSize - 1)),
            number = 0,
            currentDirection = CharacterDirection.RIGHT,
            bodyColor = SnekPink,
            headColor = SnekPinkHead,
            score = 0
        ),
        Character(
            body = listOf(Pair(boardSize - 1, 0)),
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
        ),
        Character(
            body = listOf(Pair(boardSize, boardSize)),
            number = 3,
            currentDirection = CharacterDirection.RIGHT,
            bodyColor = SnekOrange,
            headColor = SnekOrangeHead,
            score = 0
        ),
    ).take(quantity)

    override fun getPlayersQuantity(): Int = 2

    override fun getBoardSize(): Int = 7

}