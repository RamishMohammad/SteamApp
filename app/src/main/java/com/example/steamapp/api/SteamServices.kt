package com.example.steamapp.api

import com.squareup.moshi.Json
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.Body
import retrofit2.http.POST


interface SteamWebService {
    @POST("ISteamUser/GetPlayerSummaries/v2/")
    suspend fun authenticateUser(
        @Body requestBody: AuthenticateUserRequest,
        @Query("key") apiKey: String = API_KEY,
    ) : Response<SteamAuthResponse>

    @GET("ISteamApps/GetAppList/v2/")
    suspend fun getAppList(
        @Query("key") apiKey: String = API_KEY,
        @Query("type_filter") filters : String = "game,dlc"
    ) : Response<GetAppListResponse>

    companion object {
        const val BASE_URL = "https://api.steampowered.com/"
        const val API_KEY = "C5047CA4CB69A58EAD6BC1B6C8D895C7"

        fun create() : SteamWebService {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(SteamWebService::class.java)
        }
    }
}

interface SteamStoreService {
    /**
     * Returns the information about a set of apps from their app ids.
     * TODO: Verify this is correct, endpoint name suggests it is
     *
     * @param appids List of UInt app ids
     * @return A list containing the app details for each id
     */
    @GET("appdetails")
    suspend fun getAppDetails(
        @Query("appids") appids: Int,
        @Query("cc") currency: String = "us",
        @Query("l") language: String = "english",
    ) : Response<AppDetails>

    companion object {
        const val BASE_URL = "https://store.steampowered.com/api/"

        fun create() : SteamStoreService {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(SteamStoreService::class.java)
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
    val short_description: String,
    val header_image: String,
    val platforms: Platforms,
    val metacritic: MetacriticInfo
)

data class Platforms(
    val windows: Boolean,
    val mac: Boolean,
    val linux: Boolean,
    val price_overview: Pricing,
)

data class Pricing(
    val currency: String,
    val final_formatted: String,
)

data class MetacriticInfo(
    val score: Int,
    val url: String
)