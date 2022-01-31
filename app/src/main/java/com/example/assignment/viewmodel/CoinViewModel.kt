package com.example.assignment.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.model.Coin
import com.example.assignment.model.CoinDetailResponse
import com.example.assignment.model.CoinResponse
import com.example.assignment.model.ResponseBodyModel
import com.example.assignment.retrofit.ApiClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoinViewModel: ViewModel() {

    private val apiClient = ApiClient().coinApi()
    private val authorization = "coinrankingeb7ebfec146201f435562d1689c9ce74311f0677f305e42d"

    var coinLiveData: MutableLiveData<ResponseBodyModel<CoinResponse>> = MutableLiveData<ResponseBodyModel<CoinResponse>>()
    var top3CoinLiveData: MutableLiveData<ResponseBodyModel<CoinResponse>> = MutableLiveData<ResponseBodyModel<CoinResponse>>()
    var searchCoinLiveData: MutableLiveData<ResponseBodyModel<CoinResponse>> = MutableLiveData<ResponseBodyModel<CoinResponse>>()
    var coinDetailLiveData: MutableLiveData<ResponseBodyModel<Coin>> = MutableLiveData<ResponseBodyModel<Coin>>()

    fun getCoins(offset: Int) {
        val responseBodyModel: ResponseBodyModel<CoinResponse> = ResponseBodyModel()
        val call = apiClient.getCoins(5, offset, authorization)

        call.enqueue(object: Callback<ResponseBodyModel<CoinResponse>> {
            override fun onResponse(
                call: Call<ResponseBodyModel<CoinResponse>>,
                response: Response<ResponseBodyModel<CoinResponse>>
            ) {
                if (response.code() == 200) {
                    responseBodyModel.status = response.body()!!.status
                    responseBodyModel.data = response.body()!!.data
                } else {
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    responseBodyModel.status = jsonObj.get("status").toString()
                    responseBodyModel.message = jsonObj.get("message").toString()
                }
                coinLiveData.postValue(responseBodyModel)
            }

            override fun onFailure(call: Call<ResponseBodyModel<CoinResponse>>, t: Throwable) {
                Log.d("get coins: ", t.toString())
                responseBodyModel.status = "fail"
                responseBodyModel.message = t.toString()
                coinLiveData.postValue(responseBodyModel)
            }
        })
    }

    fun getTop3Coins() {
        val responseBodyModel: ResponseBodyModel<CoinResponse> = ResponseBodyModel()
        val call = apiClient.getCoins(3, 0, authorization)

        call.enqueue(object: Callback<ResponseBodyModel<CoinResponse>> {
            override fun onResponse(
                call: Call<ResponseBodyModel<CoinResponse>>,
                response: Response<ResponseBodyModel<CoinResponse>>
            ) {
                if (response.code() == 200) {
                    responseBodyModel.status = response.body()!!.status
                    responseBodyModel.data = response.body()!!.data
                } else {
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    responseBodyModel.status = jsonObj.get("status").toString()
                    responseBodyModel.message = jsonObj.get("message").toString()
                }
                top3CoinLiveData.postValue(responseBodyModel)
            }

            override fun onFailure(call: Call<ResponseBodyModel<CoinResponse>>, t: Throwable) {
                Log.d("get coins: ", t.toString())
                responseBodyModel.status = "fail"
                responseBodyModel.message = t.toString()
                top3CoinLiveData.postValue(responseBodyModel)
            }
        })
    }

    fun search(keyword: String, offset: Int) {
        val responseBodyModel: ResponseBodyModel<CoinResponse> = ResponseBodyModel()
        val call = apiClient.search(keyword, 5, offset, authorization)

        call.enqueue(object: Callback<ResponseBodyModel<CoinResponse>> {
            override fun onResponse(
                call: Call<ResponseBodyModel<CoinResponse>>,
                response: Response<ResponseBodyModel<CoinResponse>>
            ) {
                if (response.code() == 200) {
                    responseBodyModel.status = response.body()!!.status
                    responseBodyModel.data = response.body()!!.data
                } else {
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    responseBodyModel.status = jsonObj.get("status").toString()
                    responseBodyModel.message = jsonObj.get("message").toString()
                }
                searchCoinLiveData.postValue(responseBodyModel)
            }

            override fun onFailure(call: Call<ResponseBodyModel<CoinResponse>>, t: Throwable) {
                Log.d("search coins: ", t.toString())
                responseBodyModel.status = "fail"
                responseBodyModel.message = t.toString()
                searchCoinLiveData.postValue(responseBodyModel)
            }
        })
    }

    fun getDetail(uuid: String) {
        val responseBodyModel: ResponseBodyModel<Coin> = ResponseBodyModel()
        val call = apiClient.getDetail(uuid)

        call.enqueue(object: Callback<ResponseBodyModel<CoinDetailResponse>> {
            override fun onResponse(
                call: Call<ResponseBodyModel<CoinDetailResponse>>,
                response: Response<ResponseBodyModel<CoinDetailResponse>>
            ) {
                if (response.code() == 200) {
                    responseBodyModel.status = response.body()!!.status
                    responseBodyModel.data = response.body()!!.data!!.coin
                } else {
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    responseBodyModel.status = jsonObj.get("status").toString()
                    responseBodyModel.message = jsonObj.get("message").toString()
                }
                coinDetailLiveData.postValue(responseBodyModel)
            }

            override fun onFailure(call: Call<ResponseBodyModel<CoinDetailResponse>>, t: Throwable) {
                Log.d("get coin detail: ", t.toString())
                responseBodyModel.status = "fail"
                responseBodyModel.message = t.toString()
                coinDetailLiveData.postValue(responseBodyModel)
            }
        })
    }
}