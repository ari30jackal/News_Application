package com.news.tryjuardi.data.remote

import android.util.Log
import com.news.tryjuardi.data.service.NewsService
import com.news.tryjuardi.model.SourceResponse
import io.reactivex.Single
import javax.inject.Inject
import androidx.paging.PageKeyedDataSource
import com.news.tryjuardi.model.news.Article
import com.news.tryjuardi.model.news.NewsResponse

private const val INITIAL_PAGE_KEY = 1
class NewsRemoteDataSource @Inject constructor(
    private val newsService: NewsService
) {

    fun getSource(): Single<SourceResponse> {
        return newsService.getSource()
    }
fun getArticles(sources:String,page:Int):Single<NewsResponse>{
    return newsService.getArticles(sources, page)
}
}