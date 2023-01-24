package com.news.tryjuardi.data.repository

import com.news.tryjuardi.data.remote.NewsRemoteDataSource
import com.news.tryjuardi.model.SourceResponse
import com.news.tryjuardi.model.news.NewsResponse
import io.reactivex.Single
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource
) {
    fun getSource(category:String,page:Int): Single<SourceResponse> {
        return newsRemoteDataSource.getSource(category,page)
    }
    fun getArticles(source:String,page:Int):Single<NewsResponse>{
        return newsRemoteDataSource.getArticles(source,page)
    }
    fun getByKeyword(source:String,page:Int):Single<NewsResponse>{
        return newsRemoteDataSource.getByKeyword(source,page)
    }
}