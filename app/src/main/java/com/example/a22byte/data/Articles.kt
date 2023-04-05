package com.example.a22byte.data

data class Articles(
    val articles: List<News>
)

data class News(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String
    )