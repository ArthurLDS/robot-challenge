package com.example.robot_game_play.domain.repository

import com.example.robot_game_play.domain.models.Player

interface GameRepository {
    fun getPlayers(quantity: Int, boardSize: Int): List<Player>
    fun getPlayersQuantity(): Int
    fun getBoardSize(): Int
}