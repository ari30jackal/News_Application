package com.news.tryjuardi.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.news.tryjuardi.data.repository.NewsRepository
import com.news.tryjuardi.model.SourceResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SourceViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private var sourceLiveData = MutableLiveData<SourceResponse>()
    private val compositeDisposable = CompositeDisposable()

    fun getSource() {
        compositeDisposable.add(
            newsRepository.getSource()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SourceResponse>() {
                    override fun onSuccess(t: SourceResponse) {
                        sourceLiveData.value = t
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun getSourceLiveData(): MutableLiveData<SourceResponse> = sourceLiveData
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

}