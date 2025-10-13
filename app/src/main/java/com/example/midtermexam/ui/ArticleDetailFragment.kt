package com.example.midtermexam.ui

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.midtermexam.R
import com.example.midtermexam.data.Article
import com.example.midtermexam.data.Comment
import com.example.midtermexam.data.CommentRepository

class ArticleDetailFragment : Fragment() {

    private lateinit var commentAdapter: CommentAdapter
    private lateinit var currentArticle: Article

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = arguments?.getParcelable<Article>("article_data")

        if (article == null) {
            parentFragmentManager.popBackStack()
            return
        }
        currentArticle = article

        val imageView: ImageView = view.findViewById(R.id.imageViewDetail)
        val titleView: TextView = view.findViewById(R.id.textViewTitleDetail)
        val sourceView: TextView = view.findViewById(R.id.textViewSourceDetail)
        val descriptionView: TextView = view.findViewById(R.id.textViewDescriptionDetail)
        val readMoreButton: Button = view.findViewById(R.id.buttonReadMore)

        // Mengisi view dengan data dari artikel
        titleView.text = currentArticle.title
        sourceView.text = currentArticle.source?.name ?: "Sumber tidak diketahui"
        descriptionView.text = currentArticle.description ?: "Deskripsi tidak tersedia."

        Glide.with(this)
            .load(currentArticle.image)
            .placeholder(R.drawable.ic_placeholder_image)
            .error(R.drawable.ic_placeholder_image)
            .into(imageView)

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

        val recyclerViewComments: RecyclerView = view.findViewById(R.id.recyclerViewComments)
        val editTextComment: EditText = view.findViewById(R.id.editTextComment)
        val buttonAddComment: Button = view.findViewById(R.id.buttonAddComment)

        val commentsForThisArticle = CommentRepository.getCommentsForArticle(currentArticle.url ?: "")

        commentAdapter = CommentAdapter(commentsForThisArticle) { commentToReply ->
            showReplyDialog(commentToReply)
        }
        recyclerViewComments.adapter = commentAdapter

        buttonAddComment.setOnClickListener {
            val commentText = editTextComment.text.toString()
            if (commentText.isNotBlank()) {
                val newComment = Comment("Anda (You)", commentText)
                CommentRepository.addCommentToArticle(currentArticle.url ?: "", newComment)
                commentAdapter.notifyItemInserted(commentsForThisArticle.size - 1)
                editTextComment.text.clear()
                recyclerViewComments.smoothScrollToPosition(commentsForThisArticle.size - 1)
            } else {
                Toast.makeText(context, "Komentar tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showReplyDialog(commentToReply: Comment) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Balas Komentar dari ${commentToReply.author}")

        val input = EditText(requireContext())
        input.hint = "Tulis balasan Anda..."
        builder.setView(input)

        builder.setPositiveButton("Kirim") { dialog, _ ->
            val replyText = input.text.toString()
            if (replyText.isNotBlank()) {
                val newReply = Comment("Anda", replyText)
                CommentRepository.addReplyToComment(currentArticle.url ?: "", commentToReply, newReply)

                val commentsForThisArticle = CommentRepository.getCommentsForArticle(currentArticle.url ?: "")
                val position = commentsForThisArticle.indexOf(commentToReply)
                if (position != -1) {
                    commentAdapter.notifyItemChanged(position)
                }
                dialog.dismiss()
            } else {
                Toast.makeText(context, "Balasan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Batal") { dialog, _ -> dialog.cancel() }
        builder.show()
    }
}