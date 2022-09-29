package com.sky.roomdemo

import android.app.Application

/**
 * Date：2022/9/23
 * Describe:
 */
class App : Application() {
    companion object {
        lateinit var mApp: App
    }

    override fun onCreate() {
        super.onCreate()
        mApp = this
    }
}