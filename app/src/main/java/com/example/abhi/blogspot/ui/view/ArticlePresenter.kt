package com.example.abhi.blogspot.ui.view

import com.example.abhi.blogspot.injections.ActivityScoped
import com.example.abhi.blogspot.model.Comment
import com.example.abhi.blogspot.model.User
import com.example.abhi.blogspot.ui.adapters.CommentAdapter
import com.example.abhi.blogspot.ui.base.BasePresenter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

@ActivityScoped
class ArticlePresenter @Inject
constructor(): BasePresenter<ArticleMvpView>() {
    val ref = FirebaseDatabase.getInstance().reference
    val articleRef = ref.child("articles")

    fun postComment(comment: String, uid: String, authUser: User) {
        //generate unique comment id
        val commentId = FirebaseDatabase.getInstance().reference.push().key

        //create and push comment to db
        val comment = Comment(authUser, comment, commentId!!)
        articleRef.child(uid).child("comments").child(commentId!!).setValue(comment)
    }

    fun fillComments(uid: String) {
        articleRef.child(uid).child("comments").addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //intentionally left empty
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val list: ArrayList<Comment?> = ArrayList()
                    for (i in snapshot.children) {
                        list.add(i.getValue(Comment::class.java))
                    }
                    baseView?.sendCommentList(list)
                } else {
                    baseView?.sendCommentList(ArrayList())
                }
            }

        })
    }
}
