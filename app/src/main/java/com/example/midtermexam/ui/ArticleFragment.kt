package com.example.midtermexam.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.midtermexam.R
import com.example.midtermexam.api.NewsApiService
import com.example.midtermexam.data.Article
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleFragment : Fragment() {

    private lateinit var articleAdapter: ArticleAdapter
    private val apiService: NewsApiService by lazy { NewsApiService.create() }

    // Deklarasi View
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var textViewError: TextView
    private lateinit var buttonNextPage: Button
    private lateinit var buttonPrevPage: Button // Tombol baru
    private lateinit var textViewPageNumber: TextView
    private lateinit var layoutPagination: LinearLayout
    private lateinit var fabScrollToTop: FloatingActionButton

    // State Management
    private var currentPage = 1
    private var isFetching = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        initViews(view)
        setupRecyclerView()
        setupClickListeners()
        setupScrollListener()
        fetchArticles(currentPage) // Ambil data untuk halaman pertama
        return view
    }

    private fun initViews(view: View) {
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.recyclerViewArticles)
        textViewError = view.findViewById(R.id.textViewError)
        buttonNextPage = view.findViewById(R.id.buttonNextPage)
        buttonPrevPage = view.findViewById(R.id.buttonPrevPage) // Inisialisasi tombol baru
        textViewPageNumber = view.findViewById(R.id.textViewPageNumber)
        layoutPagination = view.findViewById(R.id.layout_pagination)
        fabScrollToTop = view.findViewById(R.id.fabScrollToTop)
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter { article -> /* Aksi klik item */ }
        recyclerView.adapter = articleAdapter
    }

    private fun setupClickListeners() {
        buttonNextPage.setOnClickListener {
            currentPage++
            fetchArticles(currentPage)
        }
        buttonPrevPage.setOnClickListener {
            if (currentPage > 1) {
                currentPage--
                fetchArticles(currentPage)
            }
        }
        fabScrollToTop.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }
    }

    private fun setupScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 10) fabScrollToTop.show() else if (dy < -10) fabScrollToTop.hide()
            }
        })
    }

    private fun fetchArticles(page: Int) {
        if (isFetching) return
        isFetching = true
        showLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val searchQuery = "\"kesehatan mental\" OR \"stres\" OR \"kecemasan\""
                val response = apiService.searchArticles(query = searchQuery, page = page, apiKey = API_KEY)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val articles = response.body()!!.articles

                        // ---- FILTER BAHASA DITERAPKAN DI SINI ----
                        val filteredArticles = articles.filter { isIndonesianOrEnglish(it.title) }

                        if (filteredArticles.isNotEmpty()) {
                            articleAdapter.submitList(filteredArticles)
                            recyclerView.scrollToPosition(0)
                            showContent()
                        } else {
                            // Jika setelah difilter jadi kosong, coba cari halaman berikutnya
                            Toast.makeText(context, "Tidak ada artikel relevan di halaman ini, mencoba halaman berikutnya...", Toast.LENGTH_SHORT).show()
                            if (currentPage > 0) buttonNextPage.performClick()
                        }
                    } else {
                        showError("Gagal mengambil data. Error: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("ArticleFragment", "Exception: ${e.message}", e)
                    showError("Gagal terhubung. Periksa koneksi internet Anda.")
                }
            } finally {
                isFetching = false
                withContext(Dispatchers.Main) {
                    showLoading(false)
                }
            }
        }
    }

    /**
     * Fungsi filter untuk memeriksa apakah judul hanya berisi karakter Latin standar.
     * Ini akan menyaring judul dengan aksara non-Latin (Cyrillic, Arab, dll).
     */
    private fun isIndonesianOrEnglish(title: String?): Boolean {
        if (title.isNullOrEmpty()) return false
        // Regex ini memeriksa apakah string hanya berisi huruf (a-z, A-Z), angka, spasi, dan tanda baca umum.
        val pattern = Regex("^[\\p{L}\\p{N}\\p{P}\\p{Z}]+\$")
        return title.matches(pattern)
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        buttonNextPage.isEnabled = !isLoading
        buttonPrevPage.isEnabled = !isLoading
    }

    private fun showContent() {
        textViewError.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        layoutPagination.visibility = View.VISIBLE
        textViewPageNumber.text = "Halaman: $currentPage"
        // Atur visibilitas tombol "Sebelumnya"
        buttonPrevPage.visibility = if (currentPage > 1) View.VISIBLE else View.INVISIBLE
    }

    private fun showError(message: String) {
        recyclerView.visibility = View.GONE
        layoutPagination.visibility = View.GONE
        fabScrollToTop.hide()
        textViewError.visibility = View.VISIBLE
        textViewError.text = message
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val API_KEY = "0391aa4a8d865486adc2220023fcde74"
    }
}