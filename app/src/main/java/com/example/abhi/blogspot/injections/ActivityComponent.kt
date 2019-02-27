package com.example.abhi.blogspot.injections

import com.example.abhi.blogspot.ui.article.BuildArticleActivity
import com.example.abhi.blogspot.ui.feed.ArticleFeedActivity
import com.example.abhi.blogspot.ui.login.LoginActivity
import dagger.Component

@ActivityScoped
@Component(modules = [(ActivityModule::class)], dependencies = [(ApplicationComponent::class)])
interface ActivityComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(loginActivity: ArticleFeedActivity)
    fun inject(buildArticleActivity: BuildArticleActivity)
}