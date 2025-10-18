package com.example.midtermexam.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// definisikan struktur data untuk artikel, sumber artikel, dan komentar untuk artikel
@Parcelize // Parcelize digunakan untuk menyederhanakan pembuatan class Parcelable*
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

/*
    *Parcelable merupakan interface (specifically dalam Android) untuk object serialization
    yang lebih efisien, untuk mempassing data antar fragment/activity
*/