package com.estebanposada.breakingbadtestapp.data.factory

import androidx.paging.PagedList
import com.estebanposada.breakingbadtestapp.data.database.Character
import com.estebanposada.breakingbadtestapp.data.database.CharacterDao
import com.estebanposada.breakingbadtestapp.data.server.BreakingBadApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.estebanposada.breakingbadtestapp.data.toDomain

class CharacterBoundaryCallback(
    private val dao: CharacterDao,
    private val scope: CoroutineScope,
    private val api: BreakingBadApi
) : PagedList.BoundaryCallback<Character>() {

    private var offset = 0
    private var isRequestInProgress = false
    private var allPagesGrabbed = false

    override fun onZeroItemsLoaded() {
//        super.onZeroItemsLoaded()
        requestAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Character) {
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return
        if (allPagesGrabbed) return
        scope.launch {
            val data =
                api.getCharacters(CharacterDataFactory.PAGE_SIZE, offset).map { it.toDomain() }
            if (data.isEmpty()) allPagesGrabbed = true
            dao.insertCharacter(data)
            offset += CharacterDataFactory.PAGE_SIZE
        }
    }
}