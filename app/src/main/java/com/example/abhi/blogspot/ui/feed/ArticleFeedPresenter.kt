package com.example.abhi.blogspot.ui.feed

import com.example.abhi.blogspot.injections.ActivityScoped
import com.example.abhi.blogspot.model.Article
import com.example.abhi.blogspot.ui.base.BasePresenter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

@ActivityScoped
class ArticleFeedPresenter @Inject
constructor(): BasePresenter<ArticleFeedMvpView>() {
    val ref = FirebaseDatabase.getInstance().reference
    val articleRef = ref.child("articles")

    /**
     * Updates list of articles in root/articles, list is used to display articles in activity
     */
    fun fillFeed() {
        articleRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //intentionally left empty
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val list: ArrayList<Article?> = ArrayList()
                    for (i in snapshot.children) {
                        list.add(i.getValue(Article::class.java))
                    }
                    baseView?.articleListUpdated(list)
                } else {
                    baseView?.articleListUpdated(ArrayList())
                }
            }

        })
    }

}
