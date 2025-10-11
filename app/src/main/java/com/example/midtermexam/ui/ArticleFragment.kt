package com.example.midtermexam.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
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
    private lateinit var buttonPrevPage: Button
    private lateinit var textViewPageNumber: TextView
    private lateinit var layoutPagination: LinearLayout
    private lateinit var fabScrollToTop: FloatingActionButton
    private lateinit var searchView: SearchView
    private lateinit var buttonFilter: ImageButton

    // State Management untuk Data
    private var apiQuery = "\"kesehatan mental\" OR \"stres\" OR \"kecemasan\""
    private var currentPage = 1
    private var isFetching = false
    private var originalArticleList: List<Article> = emptyList()

    // State Management untuk Filter (dipisahkan agar lebih jelas)
    private var currentSearchQuery = ""
    private var isSortedAlphabetically = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        initViews(view)
        setupRecyclerView()
        setupClickListeners()
        setupScrollListener()
        fetchArticles(currentPage)
        return view
    }

    private fun initViews(view: View) {
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.recyclerViewArticles)
        textViewError = view.findViewById(R.id.textViewError)
        buttonNextPage = view.findViewById(R.id.buttonNextPage)
        buttonPrevPage = view.findViewById(R.id.buttonPrevPage)
        textViewPageNumber = view.findViewById(R.id.textViewPageNumber)
        layoutPagination = view.findViewById(R.id.layout_pagination)
        fabScrollToTop = view.findViewById(R.id.fabScrollToTop)
        searchView = view.findViewById(R.id.searchView)
        buttonFilter = view.findViewById(R.id.buttonFilter)

        searchView.isIconified = false
        searchView.onActionViewExpanded()
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter { /* Aksi klik item */ }
        recyclerView.adapter = articleAdapter
    }

    private fun setupClickListeners() {
        buttonNextPage.setOnClickListener { currentPage++; fetchArticles(currentPage) }
        buttonPrevPage.setOnClickListener { if (currentPage > 1) { currentPage--; fetchArticles(currentPage) } }
        fabScrollToTop.setOnClickListener { recyclerView.smoothScrollToPosition(0) }

        // Listener SearchView: HANYA mengubah state query lalu panggil applyFilters
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                currentSearchQuery = newText.orEmpty()
                applyFilters() // Panggil fungsi pusat
                return true
            }
        })

        // Listener Filter Abjad: HANYA mengubah state sort lalu panggil applyFilters
        buttonFilter.setOnClickListener {
            isSortedAlphabetically = !isSortedAlphabetically
            updateFilterIcon()
            applyFilters() // Panggil fungsi pusat
        }
    }

    /**
     * FUNGSI PUSAT UNTUK SEMUA FILTER.
     * Fungsi ini menerapkan filter pencarian dan urutan secara bersamaan.
     */
    private fun applyFilters() {
        // 1. Mulai dengan daftar asli dari API
        var processedList = originalArticleList

        // 2. Terapkan filter PENCARIAN
        if (currentSearchQuery.isNotEmpty()) {
            processedList = processedList.filter { article ->
                article.title?.contains(currentSearchQuery, ignoreCase = true) == true
            }
        }

        // 3. Terapkan filter URUTAN pada hasil dari langkah sebelumnya
        if (isSortedAlphabetically) {
            processedList = processedList.sortedBy { it.title }
        }

        // 4. Tampilkan hasil akhir ke adapter
        articleAdapter.submitList(processedList)
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
                val response = apiService.searchArticles(query = apiQuery, page = page, apiKey = API_KEY)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val articles = response.body()!!.articles
                        originalArticleList = articles.filter { isIndonesianOrEnglish(it.title) }

                        // Setelah data baru datang, langsung terapkan filter yang sedang aktif
                        applyFilters()
                        recyclerView.scrollToPosition(0)
                        showContent()
                    } else {
                        showError("Gagal mengambil data. Error: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                Log.e("ArticleFragment", "Exception: ${e.message}", e)
                withContext(Dispatchers.Main) {
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

    private fun isIndonesianOrEnglish(title: String?): Boolean {
        if (title.isNullOrEmpty()) return false
        val pattern = Regex("^[\\p{L}\\p{N}\\p{P}\\p{Z}]+\$")
        return title.matches(pattern)
    }

    private fun updateFilterIcon() {
        val color = if (isSortedAlphabetically) {
            ContextCompat.getColor(requireContext(), R.color.black) // Ganti warna ini jika perlu
            Toast.makeText(context, "Diurutkan berdasarkan abjad (A-Z)", Toast.LENGTH_SHORT).show()
        } else {
            ContextCompat.getColor(requireContext(), android.R.color.darker_gray)
            Toast.makeText(context, "Urutan dikembalikan ke semula", Toast.LENGTH_SHORT).show()
        }
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

