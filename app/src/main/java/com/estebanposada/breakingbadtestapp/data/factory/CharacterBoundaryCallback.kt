package com.estebanposada.breakingbadtestapp.data.factory

import androidx.paging.PagedList
import com.estebanposada.breakingbadtestapp.data.database.Character
import com.estebanposada.breakingbadtestapp.data.database.CharacterDao
import com.estebanposada.breakingbadtestapp.data.repository.CharactersRepository.Companion.PAGE_SIZE
import com.estebanposada.breakingbadtestapp.data.server.BreakingBadApi
import com.estebanposada.breakingbadtestapp.data.toDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharacterBoundaryCallback(
    private val dao: CharacterDao,
    private val scope: CoroutineScope,
    private val api: BreakingBadApi
) : PagedList.BoundaryCallback<Character>() {

    private var offset = 0
    private var allPagesGrabbed = false

    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Character) {
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (allPagesGrabbed) return
        scope.launch {
            try {
                val data = api.getCharacters(PAGE_SIZE, offset).map { it.toDomain() }
                if (data.isEmpty()) allPagesGrabbed = true
                dao.insertCharacters(data)
                offset += PAGE_SIZE
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}