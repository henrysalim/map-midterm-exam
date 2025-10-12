package com.example.midtermexam.data

object CommentRepository {

    private val commentsMap = mutableMapOf<String, MutableList<Comment>>()

    init {
        val dummyUrl1 = "https://www.example.com/stres-kerja"
        val firstComment = Comment("Livia", "Artikel ini sangat membantu! Teknik pernapasan benar-benar efektif.")
        firstComment.replies.add(Comment("Admin", "Terima kasih atas masukannya!"))
        commentsMap[dummyUrl1] = mutableListOf(
            firstComment,
            Comment("Budi", "Tidur yang cukup juga penting, jangan begadang!")
        )

        val dummyUrl2 = "https://www.example.com/tips-meditasi"
        commentsMap[dummyUrl2] = mutableListOf(
            Comment("Sari", "Baru pertama kali coba meditasi, ternyata menenangkan sekali.")
        )
    }

    fun getCommentsForArticle(articleUrl: String): MutableList<Comment> {
        return commentsMap.getOrPut(articleUrl) { mutableListOf() }
    }
    fun addCommentToArticle(articleUrl: String, newComment: Comment) {
        getCommentsForArticle(articleUrl).add(newComment)
    }

    fun addReplyToComment(articleUrl: String, parentComment: Comment, newReply: Comment) {
        val comments = getCommentsForArticle(articleUrl)
        comments.find { it == parentComment }?.replies?.add(newReply)
    }
}