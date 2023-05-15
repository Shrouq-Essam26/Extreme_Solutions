package com.example.mywe.presentation.di

import com.example.mywe.BuildConfig
import com.example.mywe.ErrorHandler
import com.example.mywe.data.api.Api
import com.example.mywe.data.model.Constants
import com.example.mywe.data.source.PreferencesHelper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesApiHolder() = RemoteApiHolder()

    @Provides
    @Singleton
    fun providesErrorHandler() = ErrorHandler()
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit, remoteApiHolder: RemoteApiHolder): Api {
        val serviceApi = retrofit.create(Api::class.java)
        remoteApiHolder.setRemoteApi(serviceApi)
        return serviceApi
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else  HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun provideAuthenticator(
        remoteApiHolder: RemoteApiHolder,
        preferencesHelper: PreferencesHelper
    ): MyAuthenticator {
        return MyAuthenticator(remoteApiHolder = remoteApiHolder, sharedPref = preferencesHelper)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor,
                            authenticator: MyAuthenticator): OkHttpClient {
//        val certificatePinner : CertificatePinner = CertificatePinner.Builder()
//            .add(
//                BuildConfig.URL,
//                "sha256/Zi7WyG7/3sg3UCgKI7P8BcO8Lz/5zMHcPmyGZGAw4+c="
//            ).build()

        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.authenticator(authenticator)
//        if(!BuildConfig.DEBUG)
//            okHttpClient.certificatePinner(certificatePinner)
//        okHttpClient.build()


        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,
                        converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .build()
    }
}