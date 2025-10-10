package com.example.midtermexam.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.midtermexam.R
import com.example.midtermexam.data.Article

// Menggunakan ListAdapter untuk performa yang lebih efisien
class ArticleAdapter(private val onItemClicked: (Article) -> Unit) :
    ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(DiffCallback) {

    // ViewHolder bertanggung jawab untuk menampung view dari setiap item
    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val articleImage: ImageView = itemView.findViewById(R.id.imageViewArticle)
        private val articleTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val articleSource: TextView = itemView.findViewById(R.id.textViewSource)

        // Fungsi untuk mengikat data Artikel ke view
        fun bind(article: Article) {
            articleTitle.text = article.title
            articleSource.text = article.source?.name ?: "Sumber tidak diketahui"
            // Validasi URL gambar sebelum memuatnya
            if (!article.image.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(article.image)
                    .placeholder(R.drawable.ic_placeholder_image)
                    .error(R.drawable.ic_placeholder_image)
                    .into(articleImage)
            } else {
                // Jika tidak ada gambar, tampilkan placeholder saja
                Glide.with(itemView.context)
                    .load(R.drawable.ic_placeholder_image)
                    .into(articleImage)
                // Opsional: Tambahkan log untuk melihat artikel mana yang tidak punya gambar
                Log.d("ArticleAdapter", "Artikel '${article.title}' tidak memiliki gambar.")
            }
        }
    }

    // Membuat ViewHolder baru saat RecyclerView membutuhkannya
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_article, parent, false)
        return ArticleViewHolder(view)
    }

    // Menghubungkan data dari posisi tertentu ke ViewHolder
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
        // Menambahkan listener klik pada setiap item
        holder.itemView.setOnClickListener {
            onItemClicked(article)
        }
    }

    // DiffCallback untuk menghitung perbedaan antar list agar update lebih efisien
    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}