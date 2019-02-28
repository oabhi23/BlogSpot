package com.example.abhi.blogspot.ui.view

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import com.example.abhi.blogspot.R
import com.example.abhi.blogspot.model.Article
import com.example.abhi.blogspot.model.User
import com.example.abhi.blogspot.ui.base.BaseActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view_article.*
import javax.inject.Inject

class ArticleActivity: BaseActivity() {

    @Inject lateinit var articlePresenter: ArticlePresenter
    private lateinit var authUser: User
    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()

        //retrieve user and article objects sent from feed
        val bundle = intent.getBundleExtra("bundle_user")
        authUser = bundle.getParcelable("user_object") as User
        article = bundle.getParcelable("selected_article") as Article

        setContentView(R.layout.activity_view_article)

        var comment: TextInputEditText = findViewById(R.id.comment)

        btn_post.setOnClickListener {
            articlePresenter.postComment(comment.text.toString())
        }

        setViews(article)
    }

    private fun setViews(article: Article) {
        title_view.text = article.title
        author_view.text = article.author!!.name
        content_view.text = article.content

        //set image view
        if (article.picture == "") {
            image_view.setImageResource(R.drawable.default_profile_picture)
        } else {
            Picasso.with(applicationContext)
                .load(article.picture)
                .fit()
                .centerCrop()
                .into(image_view)
        }
    }
}