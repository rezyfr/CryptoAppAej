package com.rezyfr.cryptoapp.http

import com.rezyfr.cryptoapp.data.RemoteRootCryptoFeed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

interface RetrofitClient {
    fun get(): Flow<HttpClientResult<RemoteRootCryptoFeed>>
}

class CryptoFeedHttpClient(
    private val api: CryptoFeedService
) : RetrofitClient {
    override fun get(): Flow<HttpClientResult<RemoteRootCryptoFeed>> = flow {
        emit(execute { api.getCryptoFeed() })
    }.flowOn(Dispatchers.IO)
}

suspend fun <T> execute(block: suspend () -> T): HttpClientResult<T> {
    return try {
        HttpClientResult.Success(block())
    } catch (throwable: Throwable) {
        when (throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    in 300..309 -> HttpClientResult.Error(NetworkClientException("Redirection"))
                    in 400..499 -> HttpClientResult.Error(
                        NetworkClientException(
                            throwable.response()?.message()
                        )
                    )

                    in 500..599 -> HttpClientResult.Error(NetworkClientException("Server Error"))
                    else -> HttpClientResult.Error(NetworkClientException("Unknown Error"))
                }
            }
            else -> HttpClientResult.Error(throwable)
        }
    }
}

data class NetworkClientException(override val message: String? = null) : Throwable(message)