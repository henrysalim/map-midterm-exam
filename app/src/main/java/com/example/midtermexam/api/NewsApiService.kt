package com.example.midtermexam.api

import com.example.midtermexam.data.NewsApiResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// File: api/GNewsApiService.kt
// Di file NewsApiService.kt
interface NewsApiService {
    @GET("api/v4/search")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("lang") lang: String = "id",
        @Query("country") country: String = "id",
        @Query("page") page: Int, // <-- TAMBAHKAN INI
        @Query("apikey") apiKey: String
    ): Response<NewsApiResponse>


    companion object {
        private const val BASE_URL = "https://gnews.io/" // <-- BASE URL BARU

        fun create(): NewsApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(NewsApiService::class.java)
        }
    }
}