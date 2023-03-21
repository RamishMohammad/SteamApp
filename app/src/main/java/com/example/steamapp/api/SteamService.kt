package com.example.steamapp.api

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

    companion object {
        const val BASE_URL = "https://api.steampowered.com/"
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


