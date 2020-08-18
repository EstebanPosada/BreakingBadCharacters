package com.estebanposada.breakingbadtestapp.data.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.estebanposada.breakingbadtestapp.data.database.Character
import com.estebanposada.breakingbadtestapp.data.database.CharacterDao
import com.estebanposada.breakingbadtestapp.data.server.BreakingBadApi
import com.estebanposada.breakingbadtestapp.ui.main.CharacterDataSource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class CharacterDataFactory @Inject constructor(
    private val dao: CharacterDao,
    private val scope: CoroutineScope,
    private val api: BreakingBadApi
) : DataSource.Factory<Int, Character>() {

    private val sourceLiveData = MutableLiveData<CharacterDataSource>()

    override fun create(): DataSource<Int, Character> {
        val source =
            CharacterDataSource(
                dao,
                scope,
                api
            )
        sourceLiveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 15

        fun pagedListConfig() = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
    }
}