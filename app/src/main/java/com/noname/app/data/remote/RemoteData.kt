package com.noname.app.data.remote

import com.noname.app.data.Model.RepositoryDto
import com.noname.app.data.Resource
import com.noname.app.data.api.ApiHomeInterface
import com.noname.app.ui.Library.utils.NetworkConnectivity
import com.noname.app.data.error.NETWORK_ERROR
import com.noname.app.data.error.NO_INTERNET_CONNECTION
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class RemoteData constructor(
    private val serviceGenerator: Retrofit,
    private val networkConnectivity: NetworkConnectivity
) {

    suspend fun getUserRepositories(username: String, page: Int, perPage: Int): Resource<List<RepositoryDto>> {
        return try {
            val recipesService = serviceGenerator.create(ApiHomeInterface::class.java)
            val response = recipesService.getUserRepositories(username, page, perPage)

            if (response.isSuccessful) {
                Resource.Success(data = response.body() ?: emptyList())
            } else {
                Resource.DataError(errorCode = response.code())
            }

        } catch (e: UnknownHostException) {
            Resource.DataError(errorCode = -101) // No internet
        } catch (e: ConnectException) {
            Resource.DataError(errorCode = -102) // Server unreachable
        } catch (e: SocketTimeoutException) {
            Resource.DataError(errorCode = -103) // Timeout
        } catch (e: Exception) {
            Resource.DataError(errorCode = -100) // Unknown error
        }
    }



    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}
