package com.task.marvel.ui.dashboard

import androidx.lifecycle.LiveData
import com.task.marvel.data.dtos.responsedtos.characters.Character
import com.task.marvel.data.dtos.responsedtos.comics.Comic
import com.task.marvel.ui.dashboard.characteradapter.CharacterListAdapter
import com.task.marvel.ui.dashboard.comicadapter.ComicListAdapter
import com.task.marvel.utils.base.interfaces.IBaseViewModel
import com.task.marvel.utils.base.sealed.UIEvent
import com.task.marvel.utils.uikit.pagination.PaginatedRecyclerView

interface IDashboardVM : IBaseViewModel {
    var mCharacterPageNumber: Int
    val characterAdapter: CharacterListAdapter
    var isFirstTimeCharacterFetching: Boolean


    var mComicPageNumber: Int
    val comicAdapter: ComicListAdapter
    var isFirstTimeComicFetching: Boolean

    val uiState: LiveData<UIEvent>
    val uiComicState: LiveData<UIEvent>
    val heroLists: LiveData<List<Character>>
    val comicLists: LiveData<List<Comic>>
    val totalHeroLists: LiveData<List<Character>>
    val totalComicLists: LiveData<List<Comic>>
    val isCharactersSuccess: LiveData<Boolean>
    val isComicsSuccess: LiveData<Boolean>
    val isCharactersCompleted: LiveData<Boolean>
    val isComicsCompleted: LiveData<Boolean>

    fun getCharacters(
        apiKey: String,
        hash: String,
        ts: String,
        offset: Int
    )

    fun getComics(
        apiKey: String,
        hash: String,
        ts: String,
        offset: Int
    )

    fun updateTotalCharacterList(list: List<Character>)
    fun updateTotalComicList(list: List<Comic>)
    fun getPaginationCharacterListener(): PaginatedRecyclerView.Pagination
    fun getPaginationComicListener(): PaginatedRecyclerView.Pagination
}