package com.example.abhi.blogspot.ui.view

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.LinearLayoutManager
import android.view.WindowManager
import com.example.abhi.blogspot.R
import com.example.abhi.blogspot.model.Article
import com.example.abhi.blogspot.model.Comment
import com.example.abhi.blogspot.model.User
import com.example.abhi.blogspot.ui.adapters.CommentAdapter
import com.example.abhi.blogspot.ui.base.BaseActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view_article.*
import javax.inject.Inject

class ArticleActivity: BaseActivity(), ArticleMvpView {

    @Inject lateinit var articlePresenter: ArticlePresenter
    private lateinit var authUser: User
    private lateinit var article: Article
    private var commentList: MutableList<Comment?> = ArrayList()

    private lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()

        getActivityComponent()?.let {
            it.inject(this)
            articlePresenter.attachView(this)
        }

        //retrieve user and article objects sent from feed
        val bundle = intent.getBundleExtra("bundle_user")
        authUser = bundle.getParcelable("user_object") as User
        article = bundle.getParcelable("selected_article") as Article

        setContentView(R.layout.activity_view_article)

        setViews(article)

        //back to last activity
        val drawable = applicationContext.resources.getDrawable(R.drawable.back_icon)
        drawable.setTint(resources.getColor(android.R.color.white))
        toolbar.navigationIcon = drawable
        toolbar.setNavigationOnClickListener {
            finish()
        }

        var comment: TextInputEditText = findViewById(R.id.comment)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        //get presenter to retrieve a list of comments
        articlePresenter.fillComments(article.uid)

        commentAdapter = CommentAdapter(this, commentList)
        comments_list.layoutManager = LinearLayoutManager(this)
        comments_list.adapter = commentAdapter
        commentAdapter.notifyDataSetChanged()

        //post comment to article
        btn_post.setOnClickListener {
            articlePresenter.postComment(comment.text.toString(), article.uid, authUser)
            comment.text!!.clear()
            articlePresenter.fillComments(article.uid)
        }
    }

    /**
     * Display article content on activity
     */
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

    /**
     * Retrieves comment list from presenter and updates adapter with comment list
     */
    override fun sendCommentList(list: ArrayList<Comment?>) {
        commentList = list

        commentAdapter.swap(commentList)
        commentAdapter.notifyDataSetChanged()
    }

}