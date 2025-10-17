package com.example.midtermexam.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midtermexam.api.NewsApiService
import com.example.midtermexam.data.Article
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {
    // buat variabel-variabel untuk menampung data
    // MutableLiveData digunakan untuk menyimpan data agar data
    // bersifat observable (jika berubah, ada listenernya dan kita dapat segera update)
    private val apiService = NewsApiService.Companion.create()

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    var currentPage = 1
    // filter berita pada API agar yang tampil hanya yang sesuai keyword ini
    var apiQuery = "\"kesehatan mental\" OR \"psikologis\" OR \"kecemasan\" OR \"emosi\" OR \"depresi\""

    // buat fungsi untuk fetch data article dari API
    fun fetchArticles(page: Int, apiKey: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.searchArticles(query = apiQuery, page = page, apiKey = apiKey) // cari article sesuai query yang sudah didefinisikan
                if (response.isSuccessful && response.body() != null) { // jika respon tidak kosong...
                    val filteredArticles = response.body()!!.articles.filter { isIndonesianOrEnglish(it.title) } // ambil article hanya yang berbahasa Indonesia
                    _articles.postValue(filteredArticles) // taruh data ini pada variabel _articles jika berhasil mengambil respon
                    currentPage = page
                } else {
                    _errorMessage.postValue("Gagal mengambil data: ${response.message()}") // taruh pesan ini pada variabel _errorMessage jika gagal mengambil respon
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Gagal terhubung. Periksa koneksi internet Anda.") // taruh pesan ini pada variabel _errorMessage jika gagal mengambil respon
            } finally {
                _isLoading.postValue(false) //set _isLoading menjadi false setelah semua proses selesai
            }
        }
    }

    // filter article apakah berbahasa Indonesia/Inggris menggunakan regex pada judul article
    private fun isIndonesianOrEnglish(title: String?): Boolean {
        if (title.isNullOrEmpty()) return false
        val pattern = Regex("^[\\p{L}\\p{N}\\p{P}\\p{Z}]+\$")
        return title.matches(pattern)
    }
}