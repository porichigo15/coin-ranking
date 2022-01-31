package com.example.assignment.retrofit.api

import com.example.assignment.model.CoinDetailResponse
import com.example.assignment.model.CoinResponse
import com.example.assignment.model.ResponseBodyModel
import retrofit2.Call
import retrofit2.http.*

const val COIN_URL = "/v2/"

interface CoinApi {

    @GET("coins")
    fun getCoins(@Query("limit") limit: Int, @Query("offset") offset: Int, @Header("x-access-token") authHeader: String?): Call<ResponseBodyModel<CoinResponse>>

    @GET("coins")
    fun search(@Query("search") search: String, @Query("limit") limit: Int, @Query("offset") offset: Int, @Header("x-access-token") authHeader: String?): Call<ResponseBodyModel<CoinResponse>>

    @GET("coin/{uuid}")
    fun getDetail(@Path("uuid") uuid: String): Call<ResponseBodyModel<CoinDetailResponse>>
}