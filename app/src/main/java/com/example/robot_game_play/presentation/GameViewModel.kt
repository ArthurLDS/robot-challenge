package com.example.robot_game_play.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.robot_game_play.domain.game.GameEngine
import com.example.robot_game_play.domain.models.GameState
import com.example.robot_game_play.domain.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TURN_DELAY = 500L

class GameViewModel(
    private val gameEngine: GameEngine,
    private val gameRepository: GameRepository
) : ViewModel() {

    private val _gameState: MutableStateFlow<GameState?> = MutableStateFlow(null)
    val gameState = _gameState.asStateFlow()

    fun initGame() {
        val playersQuantity = gameRepository.getPlayersQuantity()
        val boardSize = getBoardSize()
        val players = gameRepository.getPlayers(playersQuantity, boardSize)

        _gameState.update { gameEngine.initGameState(players, playersQuantity, boardSize) }

        playTurn()
    }

    fun getBoardSize() = gameRepository.getBoardSize()

    private fun playTurn() {
        viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                delay(TURN_DELAY)

                _gameState.update {
                    it?.let { gameEngine.playTurn(it) }
                }
            }
        }
    }

}