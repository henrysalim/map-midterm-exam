package com.example.midtermexam.adapter

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

class ArticleAdapter(
    private val onItemClicked: (Article) -> Unit // menerima callback onItemClicked
) :
    ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(DiffCallback) {

    // sub-class untuk menampung data article yang akan ditampilkan
    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val articleImage: ImageView = itemView.findViewById(R.id.imageViewArticle)
        private val articleTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val articleSource: TextView = itemView.findViewById(R.id.textViewSource)

        // bind variabel-variabel dengan value aslinya
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
                Glide.with(itemView.context)
                    .load(R.drawable.ic_placeholder_image)
                    .into(articleImage)
                Log.d("ArticleAdapter", "Artikel '${article.title}' tidak memiliki gambar.")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_article, parent, false)
        return ArticleViewHolder(view)
    }

    // bind article dengan data menggunakan function bind pada sub-class ArticleViewHolder
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
        holder.itemView.setOnClickListener {
            onItemClicked(article)
        }
    }

    /*
        digunakan untuk membantu Adapter mengecek apakah item dan content yang kembali dibuka adalah
        item dan content yang sama
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}