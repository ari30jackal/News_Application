package com.news.tryjuardi.utils
data class ViewState<out T>(
    val status: ResponseStatus,
    val data: T?,
    val message: String?
){
    companion object{
        fun <T> success(data: T?): ViewState<T>{
            return ViewState(ResponseStatus.SUCCESS, data, "")
        }

        fun <T> error(errorMessage: String, data: T?): ViewState<T> {
            return ViewState(ResponseStatus.ERROR, data, errorMessage)
        }

        fun <T> loading(data: T?): ViewState<T> {
            return ViewState(ResponseStatus.LOADING, data, "")
        }

        fun <T> empty(message: String): ViewState<T>{
            return ViewState(ResponseStatus.EMPTY, null, message)
        }
    }
}