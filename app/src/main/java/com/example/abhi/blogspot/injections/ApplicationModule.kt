package com.example.abhi.blogspot.injections

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule (private val application: Application) {
    @Provides
    @Singleton
    //method in charge of providing the instance for the application class
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun providesContext(): Context = application
}
