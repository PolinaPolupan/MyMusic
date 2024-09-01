package com.example.mymusic

import android.app.Application
import com.example.mymusic.core.data.sync.Sync
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyMusicApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize Sync; the system responsible for keeping data in the app up to date.
        Sync.initialize(context = this)
    }
}