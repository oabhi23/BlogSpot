package com.example.abhi.blogspot.injections

import android.app.Application
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {

    fun getApplication(): Application
}

