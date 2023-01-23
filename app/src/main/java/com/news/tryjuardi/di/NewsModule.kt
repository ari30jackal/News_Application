package com.news.tryjuardi.di

import com.news.tryjuardi.data.remote.NewsRemoteDataSource
import com.news.tryjuardi.data.repository.NewsRepository
import com.news.tryjuardi.data.service.NewsService
import com.news.tryjuardi.retrofit.NewsRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AnimalModule {

    @Provides
    @Singleton
    fun provideNewsService(): NewsService = NewsRetrofit.newsService

    @Provides
    @Singleton
    fun provideNewsRemoteDataSource(newsService: NewsService): NewsRemoteDataSource
            = NewsRemoteDataSource(newsService)

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource
    ): NewsRepository
            = NewsRepository(newsRemoteDataSource)


}