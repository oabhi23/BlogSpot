package com.example.abhi.blogspot.ui.view

import com.example.abhi.blogspot.model.Comment
import com.example.abhi.blogspot.ui.base.BaseMvpView

interface ArticleMvpView: BaseMvpView {
    fun sendCommentList(list: ArrayList<Comment?>)
}
