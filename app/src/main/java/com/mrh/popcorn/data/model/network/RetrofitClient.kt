package com.mrh.popcorn.data.model.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitClient {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private val json = Json { ignoreUnknownKeys = true }

    val api: TmdbApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("aplication/json".toMediaType()))
            //.header("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
            .build()
            .create(TmdbApi::class.java)
    }

}