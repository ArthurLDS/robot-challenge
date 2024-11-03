package com.example.robot_game_play.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.robot_game_play.domain.game.GameEngine
import com.example.robot_game_play.domain.models.GameState
import com.example.robot_game_play.domain.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

    init {
        val playersQuantity = gameRepository.getPlayersQuantity()
        val boardSize = getBoardSize()
        val players = gameRepository.getPlayers(playersQuantity, boardSize)

        _gameState.update { gameEngine.initGameState(players, playersQuantity, boardSize) }

        play(playersQuantity)
    }

    private fun play(playersQuantity: Int) {
        viewModelScope.launch {
            while (true) {
                    val player1 = async { playTurn(0) }
                    val player2 = async { playTurn(1) }

                    player1.await()
                    player2.await()
            }
        }
    }

    private suspend fun playTurn(player: Int) {
        _gameState.value?.let {
            val state = gameEngine.playTurn(it, player)
            _gameState.update { state }
        }
        delay(TURN_DELAY)
    }

    fun getBoardSize() = gameRepository.getBoardSize()

}