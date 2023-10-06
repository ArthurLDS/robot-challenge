package com.example.robot_game_play.di

import com.example.robot_game_play.data.GameRepositoryImpl
import com.example.robot_game_play.domain.repository.GameRepository
import org.koin.dsl.module

val dataModule = module {
    factory<GameRepository> { GameRepositoryImpl() }
}