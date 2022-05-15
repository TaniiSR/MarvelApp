package com.task.marvel.ui.dashboard

import androidx.lifecycle.LiveData
import com.task.marvel.data.dtos.responsedtos.Character
import com.task.marvel.ui.dashboard.adapter.CharacterListAdapter
import com.task.marvel.utils.base.interfaces.IBaseViewModel
import com.task.marvel.utils.base.sealed.UIEvent
import com.task.marvel.utils.uikit.pagination.PaginatedRecyclerView

interface IDashboardVM : IBaseViewModel {
    var mCharacterPageNumber: Int
    val characterAdapter: CharacterListAdapter
    var isFirstTimeCharacterFetching: Boolean
    val uiState: LiveData<UIEvent>
    val heroLists: LiveData<List<Character>>
    val totalHeroLists: LiveData<List<Character>>
    val isCharactersSuccess: LiveData<Boolean>
    val isCharactersCompleted: LiveData<Boolean>
    fun getCharacters(
        apiKey: String,
        hash: String,
        ts: String,
        offset: Int
    )

    fun updateTotalCharacterList(list: List<Character>)
    fun getPaginationCharacterListener(): PaginatedRecyclerView.Pagination
}