import com.rezyfr.cryptoapp.http.HttpClientResult
import retrofit2.HttpException

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