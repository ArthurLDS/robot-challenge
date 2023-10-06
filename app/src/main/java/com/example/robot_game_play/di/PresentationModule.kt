package com.example.robot_game_play.di

import com.example.robot_game_play.presentation.GameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { GameViewModel(get(), get()) }
}