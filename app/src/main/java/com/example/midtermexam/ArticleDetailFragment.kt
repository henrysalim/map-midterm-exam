package com.example.midtermexam.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
// HAPUS import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.midtermexam.R
import com.example.midtermexam.data.Article // Pastikan import ini ada

class ArticleDetailFragment : Fragment() {

    // Kita tidak lagi menggunakan 'by navArgs()'
    // private val args: ArticleDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- CARA BARU UNTUK MENGAMBIL DATA ---
        val article = arguments?.getParcelable<Article>("article_data")

        // Jika artikel berhasil diterima (tidak null)
        article?.let { currentArticle ->
            // Inisialisasi semua view
            val imageView: ImageView = view.findViewById(R.id.imageViewDetail)
            val titleView: TextView = view.findViewById(R.id.textViewTitleDetail)
            val sourceView: TextView = view.findViewById(R.id.textViewSourceDetail)
            val descriptionView: TextView = view.findViewById(R.id.textViewDescriptionDetail)
            val readMoreButton: Button = view.findViewById(R.id.buttonReadMore)

            // Isi view dengan data dari artikel
            titleView.text = currentArticle.title
            sourceView.text = currentArticle.source?.name ?: "Sumber tidak diketahui"
            descriptionView.text = currentArticle.description ?: "Deskripsi tidak tersedia."

            Glide.with(this)
                .load(currentArticle.image)
                .placeholder(R.drawable.ic_placeholder_image)
                .error(R.drawable.ic_placeholder_image)
                .into(imageView)

            // Atur listener untuk tombol
            readMoreButton.setOnClickListener {
                currentArticle.url?.let {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                        startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Tidak dapat membuka link", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}