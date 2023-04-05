package com.example.a22byte.data

class Repository {
    private val apiService = ApiService.create()
    suspend fun getAllNews(search: String, page: Int) = apiService.getNews(search, page)

}