package com.example.assignment.retrofit

import com.example.assignment.retrofit.api.COIN_URL
import com.example.assignment.retrofit.api.CoinApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.coinranking.com"

class ApiClient {

    fun coinApi(): CoinApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL + COIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CoinApi::class.java)
    }
}