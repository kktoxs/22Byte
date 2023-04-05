package com.example.a22byte.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything?apiKey=$apiKey&pageSize=$pageSize")
    suspend fun getNews(@Query("q") search: String, @Query("page") page: Int): Response<Articles>

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
        private const val apiKey = "7664e2dbc4964604b9fb16e2665aceab"
        private const val language = "ru"
        const val pageSize = 6

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}