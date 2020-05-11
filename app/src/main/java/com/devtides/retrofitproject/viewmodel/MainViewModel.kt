package com.devtides.retrofitproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devtides.retrofitproject.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        GlobalScope.launch(Dispatchers.Main) {
            onError("Exception: ${throwable.localizedMessage}")
        }
    }

    val apiResponse = MutableLiveData<List<Item>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    val compositeDisposable = CompositeDisposable()

    fun fetchData() {
        loading.value = true

        ApiCallService.call().enqueue(object : Callback<ApiCallResponse> {
            override fun onResponse(call: Call<ApiCallResponse>, response: Response<ApiCallResponse>) {
                val body = response.body()
                apiResponse.value = body?.flatten()
                error.value = null
                loading.value = false
            }

            override fun onFailure(call: Call<ApiCallResponse>, t: Throwable) {
                onError(t.localizedMessage)
            }

        })
    }

    fun fetchDataRx() {
        loading.value = true

        compositeDisposable.add(ApiCallService.callRx().callGetRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<ApiCallResponse>() {
                override fun onSuccess(t: ApiCallResponse) {
                    apiResponse.value = t.flatten()
                    error.value = null
                    loading.value = false
                }

                override fun onError(e: Throwable) {
                    onError(e.localizedMessage)
                }

            }))
    }

    private fun onError(message: String) {
        error.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        compositeDisposable.clear()
    }
}