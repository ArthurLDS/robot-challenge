package com.example.robot_game_play.domain.models

import androidx.compose.ui.graphics.Color

data class Player(
    val number: Int,
    var body: List<Pair<Int, Int>>,
    var currentDirection: MoveDirection?,
    val bodyColor: Color,
    val headColor: Color,
    var score: Int
)