package com.example.abhi.blogspot.ui.feed

import android.content.AbstractThreadedSyncAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.abhi.blogspot.R
import com.example.abhi.blogspot.model.Article
import com.example.abhi.blogspot.model.User
import com.example.abhi.blogspot.ui.adapters.ArticleAdapter
import com.example.abhi.blogspot.ui.article.BuildArticleActivity
import com.example.abhi.blogspot.ui.base.BaseActivity
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_article_feed.*
import javax.inject.Inject

class ArticleFeedActivity: BaseActivity(), ArticleFeedMvpView, ArticleAdapter.CustomCallback {
    @Inject lateinit var articleFeedPresenter: ArticleFeedPresenter
    private var articleList: MutableList<Article?> = ArrayList()
    private lateinit var authUser: User

    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()

        val bundle = intent.getBundleExtra("bundle_user")
        authUser = bundle.getParcelable("user_object") as User

        setContentView(R.layout.activity_article_feed)

        getActivityComponent()?.let {
            it.inject(this)
            articleFeedPresenter.attachView(this)
        }

        //get presenter to retrieve a list of articles
        articleFeedPresenter.fillFeed()

        //adapter set up to display article list elements
        articleAdapter = ArticleAdapter(this, articleList, this)
        articles_list.layoutManager = LinearLayoutManager(this)
        articles_list.adapter = articleAdapter
        articleAdapter.notifyDataSetChanged()


        //build new article
        fab_new_article.setOnClickListener {
            val newArticleIntent = Intent(this@ArticleFeedActivity, BuildArticleActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("user_object", authUser)
            newArticleIntent.putExtra("bundle_user", bundle)
            startActivity(newArticleIntent)
        }
    }

    /**
     * Pull in article_list_element list from presenter
     */
    override fun articleListUpdated(list: ArrayList<Article?>) {
        articleList = list

        articleAdapter.swap(articleList)
        articleAdapter.notifyDataSetChanged()
    }

    override fun onArticleClicked(current: Article?) {
//        val intent = Intent(this, ArticleActivity::class.java)
        //empty
    }

}