package com.news.tryjuardi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.news.tryjuardi.data.repository.NewsRepository
import com.news.tryjuardi.model.SourceResponse
import com.news.tryjuardi.model.news.NewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private var newsLiveData = MutableLiveData<NewsResponse>()
    private val compositeDisposable = CompositeDisposable()
    private val isErrorCode = MutableLiveData<String>()
    fun getArticle(source: String, page: Int) {
        compositeDisposable.add(
            newsRepository.getArticles(source, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsResponse>() {
                    override fun onSuccess(t: NewsResponse) {
                        newsLiveData.value = t
                    }

                    override fun onError(e: Throwable) {
                        if (e is HttpException) {

                            val errorCode = (e as HttpException).response()?.code()
                            when (errorCode) {
                                400 -> {
                                    isErrorCode.value = "Data not Found "
                                }
                            }
                            val gson = Gson()
                        }
                    }
                })
        )
    }

    fun getByKeyword(keyword: String, page: Int) {
        compositeDisposable.add(
            newsRepository.getByKeyword(keyword, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsResponse>() {
                    override fun onSuccess(t: NewsResponse) {
                        newsLiveData.value = t
                    }

                    override fun onError(e: Throwable) {


                        if (e is HttpException) {

                            val errorCode = (e as HttpException).response()?.code()
                           when (errorCode) {
                                400 -> {
                                    isErrorCode.value = "Data not Found "
                                }
                            }
                            val gson = Gson()
                        }
                    }
                })
        )
    }

    fun getNewsLiveData(): MutableLiveData<NewsResponse> = newsLiveData
    fun getErrorCodeResponse(): MutableLiveData<String> = isErrorCode
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

}