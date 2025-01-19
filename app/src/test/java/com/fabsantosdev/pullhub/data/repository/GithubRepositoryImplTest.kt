package com.fabsantosdev.pullhub.data.repository

import android.util.Log
import com.fabsantosdev.pullhub.data.remote.api.GithubApiService
import com.fabsantosdev.pullhub.data.remote.dto.GithubSearchResponse
import com.fabsantosdev.pullhub.data.remote.dto.PullRequestDto
import com.fabsantosdev.pullhub.domain.model.PullRequestQuery
import com.fabsantosdev.pullhub.domain.model.UiState
import com.google.gson.JsonParseException
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import com.fabsantosdev.pullhub.R


class GithubRepositoryImplTest {

    private lateinit var repository: GithubRepositoryImpl
    private lateinit var apiService: GithubApiService

    @Before
    fun setup() {
        apiService = mockk()
        repository = GithubRepositoryImpl(apiService)

        mockkStatic(Log::class)
        every { Log.e(any(), any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getRepositories success case`() = runBlocking {
        val pageNumber = 1
        val language = "kotlin"
        val mockResponse = mockk<GithubSearchResponse>()

        coEvery {
            apiService.getRepositories(language = language, page = pageNumber)
        } returns mockResponse

        val result = repository.getRepositories(pageNumber, language).toList()

        assertEquals(2, result.size)
        assertTrue(result[0] is UiState.Loading)
        assertTrue(result[1] is UiState.Success)
        assertEquals(mockResponse, (result[1] as UiState.Success).data)

        coVerify(exactly = 1) {
            apiService.getRepositories(language = language, page = pageNumber)
        }
    }

    @Test
    fun `getPullRequests success case`() = runBlocking {
        val query = PullRequestQuery(
            ownerName = "owner", repositoryTitle = "repo", pageNumber = 1
        )
        val mockResponse = listOf<PullRequestDto>(mockk(), mockk())

        coEvery {
            apiService.getPullRequests(query.ownerName, query.repositoryTitle, query.pageNumber)
        } returns mockResponse

        val result = repository.getPullRequests(query).toList()

        assertEquals(2, result.size)
        assertTrue(result[0] is UiState.Loading)
        assertTrue(result[1] is UiState.Success)
        assertEquals(mockResponse, (result[1] as UiState.Success).data)

        coVerify(exactly = 1) {
            apiService.getPullRequests(query.ownerName, query.repositoryTitle, query.pageNumber)
        }
    }

    @Test
    fun `getRepositories network error case`() = runBlocking {
        val pageNumber = 1
        val language = "kotlin"

        coEvery {
            apiService.getRepositories(language = language, page = pageNumber)
        } throws UnknownHostException()

        val result = repository.getRepositories(pageNumber, language).toList()

        assertEquals(2, result.size)
        assertTrue(result[0] is UiState.Loading)
        assertTrue(result[1] is UiState.Failure)
        assertEquals(
            R.string.no_internet_connection, (result[1] as UiState.Failure).errorCode
        )
    }

    @Test
    fun `getRepositories timeout error case`() = runBlocking {
        val pageNumber = 1
        val language = "kotlin"

        coEvery {
            apiService.getRepositories(language = language, page = pageNumber)
        } throws SocketTimeoutException()

        val result = repository.getRepositories(pageNumber, language).toList()

        assertEquals(2, result.size)
        assertTrue(result[0] is UiState.Loading)
        assertTrue(result[1] is UiState.Failure)
        assertEquals(
            R.string.request_timed_out_please_try_again, (result[1] as UiState.Failure).errorCode
        )
    }

    @Test
    fun `getRepositories SSL error case`() = runBlocking {
        val pageNumber = 1
        val language = "kotlin"

        coEvery {
            apiService.getRepositories(language = language, page = pageNumber)
        } throws SSLException("SSL error")

        val result = repository.getRepositories(pageNumber, language).toList()

        assertEquals(2, result.size)
        assertTrue(result[0] is UiState.Loading)
        assertTrue(result[1] is UiState.Failure)
        assertEquals(
            R.string.secure_connection_failed, (result[1] as UiState.Failure).errorCode
        )
    }


    @Test
    fun `getRepositories parse error case`() = runBlocking {
        val pageNumber = 1
        val language = "kotlin"

        coEvery {
            apiService.getRepositories(language = language, page = pageNumber)
        } throws JsonParseException("Parse error")

        val result = repository.getRepositories(pageNumber, language).toList()

        assertEquals(2, result.size)
        assertTrue(result[0] is UiState.Loading)
        assertTrue(result[1] is UiState.Failure)
        assertEquals(
            R.string.failed_to_process_the_response, (result[1] as UiState.Failure).errorCode
        )
    }
}