package com.example.steamapp.api

import com.example.steamapp.data.AppListStorage
import com.squareup.moshi.Json
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.squareup.moshi.Moshi
import com.squareup.moshi.KotlinJsonAdapterFactory
import retrofit2.http.Body
import retrofit2.http.POST


interface SteamService {
    @POST("ISteamUser/GetPlayerSummaries/v2/")
    suspend fun authenticateUser(
        @Body requestBody: AuthenticateUserRequest,
        @Query("key") apiKey: String = API_KEY,
    ) : Response<SteamAuthResponse>

    @GET("ISteamApps/GetAppList/v2/")
    suspend fun getAppList(
        @Query("key") apiKey: String = API_KEY
    ) : Response<GetAppListResponse>

    /**
     * Returns the information about a set of apps from their app ids.
     * TODO: Verify this is correct, endpoint name suggests it is
     *
     * @param appids List of UInt app ids
     * @return A list containg the app details for each id
     */
    @GET("api/appdetails")
    suspend fun getAppDetails(
        @Query("appids") appids: List<UInt>
    ) : Response<List<AppDetails>>

    companion object {
        const val BASE_URL = "https://api.steampowered.com/"
        const val STORE_URL = "https://store.steampowered.com/api/"
        const val API_KEY = "C5047CA4CB69A58EAD6BC1B6C8D895C7"

        fun create() : SteamService {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(SteamService::class.java)
        }
    }
}

data class AuthenticateUserRequest(
    @Json(name = "steamid") val steamId: String,
    @Json(name = "key") val apiKey: String
)

data class SteamAuthResponse(
    val response: SteamAuth
)

data class SteamAuth(
    val steamid: String,
    val success: Boolean,
    val ticket: String
)

data class GetAppListResponse(
    val applist: AppList
)

// Container for list of all apps returned from `getAppList()`
data class AppList(
    val apps: List<App>
)

// Bare minimum data about an app from GetAppList
data class App(
    val appid: Int,
    val name: String
)

data class AppDetails(
    val success: Boolean,
    val data: AppData?,
)

data class AppData(
    val name: String,
    val detailed_description: String,
)