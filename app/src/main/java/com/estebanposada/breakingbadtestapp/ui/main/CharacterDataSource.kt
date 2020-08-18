package com.estebanposada.breakingbadtestapp.ui.main

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.estebanposada.breakingbadtestapp.data.database.Character
import com.estebanposada.breakingbadtestapp.data.database.CharacterDao
import com.estebanposada.breakingbadtestapp.data.server.BreakingBadApi
import com.estebanposada.breakingbadtestapp.data.toDomain
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDataSource @Inject constructor(
    private val dao: CharacterDao,
    private val scope: CoroutineScope,
    private val api: BreakingBadApi
) : PageKeyedDataSource<Int, Character>() {
    private val pageSize = 15

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        fetchData(pageSize, 0) {
            callback.onResult(it, null, pageSize)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val next = params.key
        fetchData(pageSize, next) {
            callback.onResult(it, next + pageSize)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val next = params.key
        fetchData(pageSize, next) {
            callback.onResult(it, next - pageSize)
        }
    }

    private fun fetchData(pageSize: Int, offset: Int, callback: (List<Character>) -> Unit) {
        scope.launch(getJobErrorHandler()) {
            val result = api.getCharacters(pageSize, offset).map { it.toDomain() }
            dao.insertCharacter(result)
            callback(result)
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.toString())
    }

    private fun postError(message: String) {
        Log.e(TAG, "An error happened: $message")
    }

    companion object {
        const val TAG = "DataSource"
    }
}