package com.example.abhi.blogspot.ui.feed

import com.example.abhi.blogspot.model.Article
import com.example.abhi.blogspot.ui.base.BaseMvpView

interface ArticleFeedMvpView: BaseMvpView {
    fun articleListUpdated(list: ArrayList<Article?>)
}
