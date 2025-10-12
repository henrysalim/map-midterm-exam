package com.example.midtermexam.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midtermexam.api.NewsApiService
import com.example.midtermexam.data.Article
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    private val apiService = NewsApiService.create()

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    var currentPage = 1
    var apiQuery = "\"kesehatan mental\" OR \"stres\" OR \"kecemasan\""

    fun fetchArticles(page: Int, apiKey: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.searchArticles(query = apiQuery, page = page, apiKey = apiKey)
                if (response.isSuccessful && response.body() != null) {
                    val filteredArticles = response.body()!!.articles.filter { isIndonesianOrEnglish(it.title) }
                    _articles.postValue(filteredArticles)
                    currentPage = page
                } else {
                    _errorMessage.postValue("Gagal mengambil data: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Gagal terhubung. Periksa koneksi internet Anda.")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun isIndonesianOrEnglish(title: String?): Boolean {
        if (title.isNullOrEmpty()) return false
        val pattern = Regex("^[\\p{L}\\p{N}\\p{P}\\p{Z}]+\$")
        return title.matches(pattern)
    }
}