package com.example.robot_game_play.presentation

import app.cash.turbine.test
import com.example.robot_game_play.domain.game.GameEngine
import com.example.robot_game_play.domain.repository.GameRepository
import com.example.robot_game_play.testData.TestData.CHARACTER_LIST_DATA
import com.example.robot_game_play.testData.TestData.GAME_STATE_DATA
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GameViewModelTest : BaseViewModelTest() {

    private val repository: GameRepository = mockk()
    private val gameEngine: GameEngine = mockk()

    @Test
    fun `Given board game when get board size then return 10`() {
        val viewModel = GameViewModel(gameEngine, repository)

        every { repository.getBoardSize() } returns 10

        assert(viewModel.getBoardSize() == 10)
    }

    @Test
    fun `Given game when started it then create initial state`() = runBlocking {
        every { repository.getBoardSize() } returns 7
        every { repository.getPlayersQuantity() } returns 2
        every { repository.getPlayers(2, 7) } returns CHARACTER_LIST_DATA
        every { gameEngine.initGameState(CHARACTER_LIST_DATA, 2, 7) } returns GAME_STATE_DATA

        val viewModel = GameViewModel(gameEngine, repository)

        viewModel.initGame()

        viewModel.gameState.test {
            val emission2 = awaitItem()
            assertThat(emission2).isEqualTo(GAME_STATE_DATA)

            cancelAndConsumeRemainingEvents()
        }
    }

}