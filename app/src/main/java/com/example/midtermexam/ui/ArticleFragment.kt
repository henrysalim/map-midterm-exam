package com.example.midtermexam.ui

import android.os.Bundle
import android.os.Parcelable
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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.midtermexam.R
import com.example.midtermexam.data.Article
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ArticleFragment : Fragment() {

    private lateinit var articleAdapter: ArticleAdapter
    private val viewModel: ArticleViewModel by viewModels()

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

    // State Management UI
    private var originalArticleList: List<Article> = emptyList()
    private var recyclerViewState: Parcelable? = null
    private var currentSearchQuery = ""
    private var isSortedAlphabetically = false

    // Langkah 1: onCreateView HANYA untuk inflate layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    // Langkah 2: Semua interaksi dengan View dilakukan di onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Panggil semua fungsi setup di sini, SETELAH view dijamin sudah ada
        initViews(view)
        setupRecyclerView()
        setupClickListeners()
        setupScrollListener()
        observeViewModel()

        // Hanya ambil data dari API jika ViewModel belum punya data (saat pertama kali dibuka)
        if (viewModel.articles.value.isNullOrEmpty()) {
            viewModel.fetchArticles(viewModel.currentPage, API_KEY)
        }
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
        articleAdapter = ArticleAdapter { article ->
            val bundle = Bundle().apply { putParcelable("article_data", article) }
            findNavController().navigate(R.id.action_articleFragment_to_articleDetailFragment, bundle)
        }
        recyclerView.adapter = articleAdapter
    }

    private fun setupClickListeners() {
        buttonNextPage.setOnClickListener { viewModel.fetchArticles(viewModel.currentPage + 1, API_KEY) }
        buttonPrevPage.setOnClickListener {
            if (viewModel.currentPage > 1) {
                viewModel.fetchArticles(viewModel.currentPage - 1, API_KEY)
            }
        }
        fabScrollToTop.setOnClickListener { recyclerView.smoothScrollToPosition(0) }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = true.also { searchView.clearFocus() }
            override fun onQueryTextChange(newText: String?): Boolean {
                currentSearchQuery = newText.orEmpty()
                applyFilters()
                return true
            }
        })

        buttonFilter.setOnClickListener {
            isSortedAlphabetically = !isSortedAlphabetically
            updateFilterIcon()
            applyFilters()
            val message = if (isSortedAlphabetically) "Diurutkan berdasarkan abjad (A-Z)" else "Urutan dikembalikan ke semula"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            originalArticleList = articles
            applyFilters()
            showContent()
            if (recyclerViewState != null) {
                recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
                recyclerViewState = null
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let { showError(it) }
        }
    }

    private fun applyFilters() {
        var processedList = originalArticleList
        if (currentSearchQuery.isNotEmpty()) {
            processedList = processedList.filter { it.title?.contains(currentSearchQuery, ignoreCase = true) == true }
        }
        if (isSortedAlphabetically) {
            processedList = processedList.sortedBy { it.title }
        }
        articleAdapter.submitList(processedList)
    }

    private fun setupScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 10) fabScrollToTop.show() else if (dy < -10) fabScrollToTop.hide()
            }
        })
    }

    private fun updateFilterIcon() {
        val colorRes = if (isSortedAlphabetically) R.color.black else android.R.color.darker_gray
        buttonFilter.setColorFilter(ContextCompat.getColor(requireContext(), colorRes))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerViewState = recyclerView.layoutManager?.onSaveInstanceState()
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
        textViewPageNumber.text = "Halaman: ${viewModel.currentPage}"
        buttonPrevPage.visibility = if (viewModel.currentPage > 1) View.VISIBLE else View.INVISIBLE
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

