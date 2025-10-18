package com.example.midtermexam.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.midtermexam.R
import com.example.midtermexam.data.Comment

class CommentAdapter(
    // CommentAdapter menerima list comment dan callback onReplyClicked
    private val comments: MutableList<Comment>,
    private val onReplyClicked: (Comment) -> Unit
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    // sub-class CommentViewHolder untuk menampung data comment
    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val author: TextView = view.findViewById(R.id.textViewCommentAuthor)
        val text: TextView = view.findViewById(R.id.textViewCommentText)
        val replyButton: TextView = view.findViewById(R.id.buttonReply)
        val repliesContainer: LinearLayout = view.findViewById(R.id.repliesContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]

        // Tampilkan data komentar utama
        holder.author.text = comment.author
        holder.text.text = comment.text

        // Atur listener untuk tombol "Balas", mengirimkan komentar mana yang akan dibalas
        holder.replyButton.setOnClickListener {
            onReplyClicked(comment)
        }

        // tampilkan komen reply terbaru ke list reply komentar
        holder.repliesContainer.removeAllViews()
        if (comment.replies.isNotEmpty()) {
            val context = holder.itemView.context
            for (reply in comment.replies) {
                val replyView = createReplyTextView(context, reply)
                holder.repliesContainer.addView(replyView)
            }
        }
    }

    // hitung berapa banyak komen
    override fun getItemCount() = comments.size

    // atur style text reply komen
    private fun createReplyTextView(context: Context, reply: Comment): TextView {
        return TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { topMargin = 4 }
            text = "${reply.author} (membalas): ${reply.text}"
            textSize = 13f
        }
    }
}