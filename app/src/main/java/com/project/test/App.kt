package com.project.test

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.project.test.data.Repository
import com.project.test.data.networkrepository.NetworkRepository
import com.project.test.util.InstanceRegistry
import com.project.test.util.locate

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        /**
         * Object [InstanceRegistry] that saves all instances registed so that they can be used throughout the application.
         * Right now, registers an instance of:
         * [NetworkRepository], [Repository]
         */
        InstanceRegistry.register<NetworkRepository>(NetworkRepository.create())
        InstanceRegistry.register<Repository>(Repository(locate()))
    }
}