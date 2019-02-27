package com.example.abhi.blogspot

import android.app.Application
import android.content.Context
import com.example.abhi.blogspot.injections.ApplicationComponent
import com.example.abhi.blogspot.injections.ApplicationModule
import com.example.abhi.blogspot.injections.DaggerApplicationComponent

class BlogSpotApplication : Application() {

    private var applicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    fun applicationComponent(): ApplicationComponent? {
        return applicationComponent
    }

    operator fun get(context: Context) : BlogSpotApplication? {
        return context.applicationContext as? BlogSpotApplication
    }
}
