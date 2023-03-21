package com.example.steamapp

import android.app.Application
import com.example.steamapp.api.SteamService
import com.example.steamapp.data.AppListStorage
import kotlinx.coroutines.runBlocking

/**
 * This class contains logic that should only run once on application startup. We call a coroutine
 * to load the app list from the steam api and initialize our AppListStorage singleton.
 * AppListStorage.initialize() shouldn't be called anywhere else in the project unless we want to
 * overwrite the app list.
 */
class SteamApp : Application() {
    private val service = SteamService.create()

    override fun onCreate() {
        super.onCreate()
        runBlocking {
            val appListStorage  = AppListStorage.getInstance()
            service.getAppList().body()?.let { appListStorage.initialize(it.applist) }
        }
    }
}