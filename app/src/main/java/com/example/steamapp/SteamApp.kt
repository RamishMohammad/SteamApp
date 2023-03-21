package com.example.steamapp

import android.app.Application
import com.example.steamapp.api.SteamWebService
import com.example.steamapp.data.AppListStorage
import kotlinx.coroutines.*

/**
 * This class contains logic that should only run once on application startup. We call a coroutine
 * to load the app list from the steam api and initialize our AppListStorage singleton.
 * AppListStorage.initialize() shouldn't be called anywhere else in the project unless we want to
 * overwrite the app list.
 */
class SteamApp : Application() {
    private val service = SteamWebService.create()

    override fun onCreate() {
        super.onCreate()

        @OptIn(DelicateCoroutinesApi::class)
        GlobalScope.launch(Dispatchers.IO) {
            val appListStorage  = AppListStorage.getInstance()
            service.getAppList().body()?.let { appListStorage.initialize(it.applist) }
        }
    }
}