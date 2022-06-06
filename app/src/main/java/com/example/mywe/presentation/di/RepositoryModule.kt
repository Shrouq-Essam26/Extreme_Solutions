package com.example.mywe.presentation.di

import android.app.Application
import android.content.SharedPreferences
import com.example.mywe.ErrorHandler
import com.example.mywe.data.api.Api
import com.example.mywe.data.model.Constants.ENCRYPT_KEY
import com.example.mywe.data.model.Constants.PREFS_FILE
import com.example.mywe.data.source.DataSource
import com.example.mywe.data.source.PreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return com.securepreferences.SecurePreferences(application, ENCRYPT_KEY, PREFS_FILE)
    }

    @Provides
    @Singleton
    fun providePrefHelper(sharedPref: SharedPreferences): PreferencesHelper {
        return PreferencesHelper(mSharedPreferences = sharedPref)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: Api, prefsHelper: PreferencesHelper , errorHandler: ErrorHandler): DataSource = DataSource(api = api, prefsHelper = prefsHelper  , errorHandler = errorHandler)
}