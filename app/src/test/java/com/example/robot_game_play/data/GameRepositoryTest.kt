package com.example.robot_game_play.data

import com.example.robot_game_play.testData.TestData.CHARACTER_LIST_DATA
import com.example.robot_game_play.testData.TestData.CHARACTER_LIST_OF_THREE_DATA
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.google.common.truth.Truth.assertThat

@RunWith(JUnit4::class)
class GameRepositoryTest {

    private lateinit var repository: GameRepositoryImpl

    @Before
    fun setUp() {
        repository = GameRepositoryImpl()
    }

    @Test
    fun `Given quantity 2 and boardSize 7 when get players then return list of 2`() {
        val expected = CHARACTER_LIST_DATA
        val response = repository.getPlayers(2, 7)

        assertThat(expected).isEqualTo(response)
    }

    @Test
    fun `Given quantity 3 and boardSize 7 when get players then return list of 3`() {
        val expected = CHARACTER_LIST_OF_THREE_DATA
        val response = repository.getPlayers(3, 7)

        assertThat(expected).isEqualTo(response)
    }

    @Test
    fun `Given boardSize 7 when get board size then return 7`() {
        val expected = 7
        val response = repository.getBoardSize()

        assertThat(expected).isEqualTo(response)
    }

    @Test
    fun `Given players quantity of 2 when get quantity then return 2`() {
        val expected = 2
        val response = repository.getPlayersQuantity()

        assertThat(expected).isEqualTo(response)
    }
}