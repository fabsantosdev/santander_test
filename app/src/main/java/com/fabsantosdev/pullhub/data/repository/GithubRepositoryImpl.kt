package com.fabsantosdev.pullhub.data.repository

import android.util.Log
import com.fabsantosdev.pullhub.R
import com.fabsantosdev.pullhub.data.remote.api.GithubApiService
import com.fabsantosdev.pullhub.data.remote.dto.GithubSearchResponse
import com.fabsantosdev.pullhub.data.remote.dto.PullRequestDto
import com.fabsantosdev.pullhub.domain.model.PullRequestQuery
import com.fabsantosdev.pullhub.domain.model.UiState
import com.fabsantosdev.pullhub.domain.repository.GithubRepository
import com.google.gson.JsonParseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLException


class GithubRepositoryImpl @Inject constructor(
    private val apiService: GithubApiService
) : GithubRepository {

    companion object {
        private const val TAG = "GithubRepositoryImpl"
    }

    /**
     * Hierarquia de erros que podem ocorrer durante as chamadas à API.
     * Cada erro contém uma referência à sua mensagem de erro correspondente.
     */
    sealed class NetworkError(val messageResId: Int) {
        data object Network : NetworkError(R.string.no_internet_connection)
        data object Timeout : NetworkError(R.string.request_timed_out_please_try_again)
        data object SSL : NetworkError(R.string.secure_connection_failed)
        data object Parse : NetworkError(R.string.failed_to_process_the_response)
        data object Default : NetworkError(R.string.an_unexpected_error_occurred)

        sealed class Http(messageResId: Int) : NetworkError(messageResId) {
            data object Unauthorized :
                Http(R.string.authentication_failed_please_check_your_credentials)

            data object Forbidden : Http(R.string.access_denied_you_don_t_have_permission_to_access_this_resource)

            data object NotFound : Http(R.string.the_requested_resource_was_not_found)
            data object RateLimit : Http(R.string.rate_limit_exceeded_please_try_again_later)
            data object ServerError : Http(R.string.server_error_please_try_again_later)
            data object Unknown : Http(R.string.unknown_error)
        }
    }

    override suspend fun getRepositories(
        pageNumber: Int,
        language: String,
        itemsPerPage: Int
    ): Flow<UiState<GithubSearchResponse>> = flow {
        emit(UiState.Loading)
        val response = apiService.getRepositories(
            language = language,
            page = pageNumber
        )
        emit(UiState.Success(response))
    }.catch { throwable ->
        emit(handleError(throwable))
    }

    override suspend fun getPullRequests(
        pullRequestQuery: PullRequestQuery,
        itemsPerPage: Int
    ): Flow<UiState<List<PullRequestDto>>> = flow {
        emit(UiState.Loading)
        val response = apiService.getPullRequests(
            pullRequestQuery.ownerName,
            pullRequestQuery.repositoryTitle,
            pullRequestQuery.pageNumber
        )
        emit(UiState.Success(response))
    }.catch { throwable ->
        emit(handleError(throwable))
    }

    private fun handleError(throwable: Throwable): UiState.Failure {
        Log.e(TAG, "Repository error", throwable)

        val error = when (throwable) {
            is UnknownHostException -> NetworkError.Network
            is SocketTimeoutException -> NetworkError.Timeout
            is SSLException -> NetworkError.SSL
            is JsonParseException -> NetworkError.Parse
            is HttpException -> handleHttpError(throwable)
            else -> NetworkError.Default
        }

        return UiState.Failure(errorCode = error.messageResId, errorMessage = null)
    }

    private fun handleHttpError(exception: HttpException): NetworkError = when (exception.code()) {
        401 -> NetworkError.Http.Unauthorized
        403 -> {
            val response = exception.response()
            val rateLimitRemaining = response?.headers()?.get("X-RateLimit-Remaining")?.toIntOrNull()

            if (rateLimitRemaining == 0) {
                NetworkError.Http.RateLimit
            } else {
                NetworkError.Http.Forbidden
            }
        }
        404 -> NetworkError.Http.NotFound
        429 -> NetworkError.Http.RateLimit
        in 500..504 -> NetworkError.Http.ServerError
        else -> NetworkError.Http.Unknown
    }
}