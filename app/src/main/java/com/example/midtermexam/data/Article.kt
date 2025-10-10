package com.example.midtermexam.data

// File: data/Article.kt
data class Article(
    val title: String?,
    val description: String?,
    val content: String?,
    val url: String?,
    val image: String?, // <-- PERUBAHAN DARI urlToImage
    val publishedAt: String?,
    val source: Source?
)

data class Source(
    val name: String?,
    val url: String?
)