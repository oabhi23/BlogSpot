package com.example.abhi.blogspot.ui.base

open class BasePresenter<T: BaseMvpView>: Presenter<T> {
    var baseView: T? = null

    override fun attachView(baseView: T) {
        this.baseView = baseView
    }

    override fun detachView() {
        this.baseView = null
    }

    override fun isViewAttached(): Boolean {
        return baseView == null
    }
}