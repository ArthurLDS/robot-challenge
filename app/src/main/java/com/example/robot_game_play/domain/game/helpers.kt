package com.example.robot_game_play.domain.game

import java.util.Random

fun rand(max: Int): Int {
    return Random().nextInt(max)
}