package com.news.tryjuardi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.news.tryjuardi.data.repository.NewsRepository
import com.news.tryjuardi.model.SourceResponse
import com.news.tryjuardi.model.news.NewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private var newsLiveData = MutableLiveData<NewsResponse>()
    private val compositeDisposable = CompositeDisposable()

    fun getNews(source:String,page:Int) {
        compositeDisposable.add(
            newsRepository.getArticles(source, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsResponse>() {
                    override fun onSuccess(t:NewsResponse) {
                        newsLiveData.value = t
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun getNewsLiveData(): MutableLiveData<NewsResponse> =newsLiveData
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

}