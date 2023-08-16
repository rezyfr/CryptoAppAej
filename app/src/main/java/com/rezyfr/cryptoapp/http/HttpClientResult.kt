package com.rezyfr.cryptoapp.http

sealed class HttpClientResult<T>(val data: T? = null, val throwable: Throwable? = null) {
    class Success<T>(data: T) : HttpClientResult<T>(data)
    class Error<T>(throwable: Throwable) : HttpClientResult<T>(throwable = throwable)
}