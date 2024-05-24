package org.macamps.musicplayer.app

import android.app.Application

class App : Application() {
    companion object {
        lateinit var myApp: App

        fun getAppInstance(): App {
            return myApp
        }
    }

    override fun onCreate() {
        super.onCreate()
        myApp = this
    }
}
