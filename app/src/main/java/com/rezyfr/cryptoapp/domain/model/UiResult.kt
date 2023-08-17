package com.rezyfr.cryptoapp.domain.model

sealed class UiResult<T>(val data: T? = null, val throwable: Throwable? = null) {
    class Success<T>(data: T) : UiResult<T>(data)
    class Error<T>(throwable: Throwable) : UiResult<T>(throwable = throwable)
    class Loading<T> : UiResult<T>()
    class Empty<T> : UiResult<T>()
}

class InvalidData : Throwable()
class NoConnectivity : Throwable()
class UnexpectedValueRepresentation : Throwable()