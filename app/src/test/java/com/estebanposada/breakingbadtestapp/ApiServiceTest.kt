package com.estebanposada.breakingbadtestapp

import org.junit.runner.RunWith
import org.junit.Rule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.estebanposada.breakingbadtestapp.data.server.BreakingBadApi
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class ApiServiceTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: BreakingBadApi

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp(){
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(provideHttpInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BreakingBadApi::class.java)
    }

    private fun provideHttpInterceptor(): OkHttpClient =
        HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().addInterceptor(this).build()
        }

    @After
    fun finish(){
        mockWebServer.shutdown()
    }

    @Test
    fun getCharacters(){
        runBlocking {
            enqueueResponse("Characters.json")

            val characters = service.getCharacters(10, 0)

            MatcherAssert.assertThat(characters, CoreMatchers.notNullValue())
            MatcherAssert.assertThat(characters.size, CoreMatchers.`is`(10))
            MatcherAssert.assertThat(characters[0].char_id, CoreMatchers.`is`(1))

        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse.setBody(source.readString(Charsets.UTF_8))
        )
    }


}