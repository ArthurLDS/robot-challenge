package com.example.robot_game_play.presentation

import kotlinx.coroutines.Dispatchers
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}