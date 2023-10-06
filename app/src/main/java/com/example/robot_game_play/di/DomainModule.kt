package com.example.robot_game_play.di

import com.example.robot_game_play.domain.game.GameEngine
import org.koin.dsl.module

val domainModule = module {
    single { GameEngine() }
}