package com.example.midtermexam.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String?,
    val description: String?,
    val content: String?,
    val url: String?,
    val image: String?,
    val publishedAt: String?,
    val source: Source?
) : Parcelable

@Parcelize
data class Source(
    val name: String?,
    val url: String?
) : Parcelable

data class Comment(
    val author: String,
    val text: String,
    val replies: MutableList<Comment> = mutableListOf() // Daftar untuk menampung balasan
)