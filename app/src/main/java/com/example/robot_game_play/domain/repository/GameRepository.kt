package com.example.robot_game_play.domain.repository

import com.example.robot_game_play.domain.models.Character

interface GameRepository {
    fun getCharacters(quantity: Int, boardSize: Int): List<Character>
    fun getPlayersQuantity(): Int
    fun getBoardSize(): Int
}