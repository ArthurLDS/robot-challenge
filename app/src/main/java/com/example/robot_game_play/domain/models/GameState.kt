package com.example.robot_game_play.domain.models

data class GameState(
    val foodCoord: Pair<Int, Int>,
    val playerList: List<Player>,
    val currentPlayerTurn: Int,
    var stuckPlayers: Int,
    var stalemateRounds: Int,
)

