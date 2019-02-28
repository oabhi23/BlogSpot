package com.example.abhi.blogspot.injections

import com.example.abhi.blogspot.ui.build.BuildArticleActivity
import com.example.abhi.blogspot.ui.feed.ArticleFeedActivity
import com.example.abhi.blogspot.ui.login.LoginActivity
import com.example.abhi.blogspot.ui.view.ArticleActivity
import dagger.Component

@ActivityScoped
@Component(modules = [(ActivityModule::class)], dependencies = [(ApplicationComponent::class)])
interface ActivityComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(loginActivity: ArticleFeedActivity)
    fun inject(buildArticleActivity: BuildArticleActivity)
    fun inject(articleActivity: ArticleActivity)
}