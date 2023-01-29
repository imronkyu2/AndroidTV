package com.example.androidtv.ui.inject

import com.example.androidtv.data.ApiService
import com.example.androidtv.data.Repository
import com.example.androidtv.ui.viewmodel.AndroidTVViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InjectAndroidTv {

    const val BASE_URL = "https://user1673281842743.requestly.dev"

    private fun getOkhttp(): OkHttpClient {
        val okHttpClient = HttpLoggingInterceptor()
        okHttpClient.setLevel(HttpLoggingInterceptor.Level.BODY)
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(okHttpClient)
        return builder.build()
    }

    private fun getRetrofit(): Retrofit {
        val converterFactory = GsonConverterFactory.create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkhttp())
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun getRepository(): Repository {
        val apiService = getRetrofit().create(ApiService::class.java)
        return Repository(apiService)
    }

    fun getViewModel(): AndroidTVViewModel {
        return AndroidTVViewModel(getRepository())
    }
}