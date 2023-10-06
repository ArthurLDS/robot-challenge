package com.example.robot_game_play

import android.app.Application
import com.example.robot_game_play.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level.ERROR

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidLogger(ERROR)
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}