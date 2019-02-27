package com.example.abhi.blogspot.ui.login

import com.example.abhi.blogspot.model.User
import com.example.abhi.blogspot.ui.base.BaseMvpView

interface LoginMvpView: BaseMvpView{
    fun startArticleFeedIntent(user: User?)
}
