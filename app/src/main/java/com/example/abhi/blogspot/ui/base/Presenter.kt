package com.example.abhi.blogspot.ui.base

interface Presenter<in T: BaseMvpView> {
    fun attachView(baseView: T)

    fun detachView()

    fun isViewAttached(): Boolean

}