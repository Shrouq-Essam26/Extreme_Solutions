package com.example.mywe
import com.example.mywe.ErrorEntity
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.net.ssl.HttpsURLConnection

class ErrorHandler  {
    fun getError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is SocketTimeoutException -> ErrorEntity.NetworkError("نأسف، لقد حدث خطأ في الإتصال.. برجاء المحاولة في وقت لاحق")
            is IOException -> ErrorEntity.NetworkError("نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق")
            is HttpException -> {
                val errorObject = JSONObject(throwable.response()!!.errorBody()?.string())
                val message = errorObject.getString("exception")
                when (throwable.code()) {
                    HttpsURLConnection.HTTP_CLIENT_TIMEOUT -> ErrorEntity.NetworkError(message)
                    HttpsURLConnection.HTTP_INTERNAL_ERROR -> ErrorEntity.NetworkError(message)
                    HttpsURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied
                    HttpsURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable
                    HttpsURLConnection.HTTP_BAD_REQUEST -> ErrorEntity.BadRequest
                    HttpsURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound
                    else -> ErrorEntity.UnKnownError
                }
            }
            else -> ErrorEntity.UnKnownError
        }
    }
}