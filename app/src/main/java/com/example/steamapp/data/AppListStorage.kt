package com.example.steamapp.data

import com.example.steamapp.api.App
import com.example.steamapp.api.AppList

/**
 * A singleton class that stores the names and app ids of all apps from the Steam Web API.
 * This is highly inefficient, but it is the only way to search apps on Steam due to its
 * API's design.
 *
 * By default, AppListStorage doesn't contain any data, and must be initialized after it is
 * instantiated. It must first be initialized with its `initialize()` function before use otherwise
 * it will cause a runtime error.
 *
 * As a singleton, whenever access to the app list is needed, the `getInstance()` function should be
 * called instead of initializing a new object.
 *
 * Singleton boilerplate is referenced from here: https://www.baeldung.com/kotlin/singleton-classes
 */
class AppListStorage private constructor(){
    companion object {
        @Volatile
        private var instance: AppListStorage? = null
        private lateinit var appList: AppList

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: AppListStorage().also { instance = it }
            }
    }

    /**
     * Initialize the AppListStorage with the values for its appList. This can be called whenever
     * the appList
     */
    fun initialize(newAppList: AppList) {
        appList = newAppList
    }

    /**
     * Returns all apps where their name contains the substring from the search parameter.
     *
     * @param search Substring to search each app name for
     * @return List of Apps whose name contains the substring
     */
    fun filterListByName(search: String): List<App> {
        return appList.apps.filter { it.name.contains(search) }
    }

    /**
     * Returns the name of the app with the corresponding id.
     *
     * @param id Id of the app
     * @return App's name as string, null if not found
     */
    fun getAppNameById(id: Int): String? {
        return appList.apps.find { it.appid == id }?.name
    }

    /**
     * Returns an app id corresponding to the name parameter.
     *
     * Search works by finding the list of all apps whose name contains the name parameter as a
     * substring and returns the id of the first result.
     *
     * @param name Substring to search for
     * @return App id of the searched name or null if not found.
     */
    fun getAppIdByFuzzyName(name: String): Int? {
        return try {
            this.filterListByName(name).first().appid
        } catch (e: NoSuchElementException) {
            null
        }
    }

    /**
     * Returns the app id corresponding to the name parameter.
     *
     * The name parameter must perfectly match the one contained in the app list otherwise this
     * will return null. This version exists because its presumably faster than the filter method.
     *
     * @param name The exact App name to search for
     * @return App id of the searched name or null if not found.
     */
    fun getAppIdByExactName(name: String): Int? {
        return appList.apps.find { it.name == name }?.appid
    }
}