package com.example.midtermexam.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScanResult(
    val id: String,
    val prediction: String, // Contoh: "Tingkat Depresi: Sedang"
    val confidence: Double, // Contoh: 0.75 (75%)
    val timestamp: Long,
    val imageUrl: String, // Bisa URL dari internet atau path file lokal
    var isPinned: Boolean = false
) : Parcelable
