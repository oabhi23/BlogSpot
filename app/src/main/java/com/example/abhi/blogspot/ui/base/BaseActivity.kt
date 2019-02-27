package com.example.abhi.blogspot.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.abhi.blogspot.BlogSpotApplication
import com.example.abhi.blogspot.injections.ActivityComponent
import com.example.abhi.blogspot.injections.DaggerActivityComponent

abstract class BaseActivity: AppCompatActivity(), BaseMvpView {
    private lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder()
            .applicationComponent((application as BlogSpotApplication).applicationComponent())
            .build()

    }

    fun getActivityComponent(): ActivityComponent = activityComponent
}