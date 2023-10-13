package com.example.robot_game_play.domain.models

import androidx.compose.ui.graphics.Color

const val FIRST_PLAYER = 0
const val SECOND_PLAYER = 1
const val THIRD_PLAYER = 2
const val FOURTH_PLAYER = 3

data class Player(
    val number: Int,
    var body: List<Pair<Int, Int>>,
    var currentDirection: MoveDirection?,
    val bodyColor: Color,
    val headColor: Color,
    var score: Int
) {

    fun setToInitialPosition(boardSize: Int) {
        val initialPosition = when(number) {
            FIRST_PLAYER -> listOf(Pair(0, boardSize - 1))
            SECOND_PLAYER -> listOf(Pair(boardSize - 1, 0))
            THIRD_PLAYER -> listOf(Pair(0, 0))
            FOURTH_PLAYER -> listOf(Pair(boardSize - 1, boardSize - 1))
            else -> listOf()
        }
        body = initialPosition
    }
}