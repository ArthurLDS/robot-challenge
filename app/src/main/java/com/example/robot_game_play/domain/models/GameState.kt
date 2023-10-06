package com.example.robot_game_play.domain.models

data class GameState(
    val foodCoord: Pair<Int, Int>,
    val characterList: List<Character>,
    val currentCharacterTurn: Int,
    var stuckCharacters: Int,
    var stalemateRounds: Int,
)

