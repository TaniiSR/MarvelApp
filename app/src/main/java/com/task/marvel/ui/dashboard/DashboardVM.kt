package com.task.marvel.ui.dashboard


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.marvel.BuildConfig
import com.task.marvel.data.dtos.responsedtos.characters.Character
import com.task.marvel.data.dtos.responsedtos.comics.Comic
import com.task.marvel.data.remote.baseclient.ApiResponse
import com.task.marvel.domain.IDataRepository
import com.task.marvel.ui.dashboard.characteradapter.CharacterListAdapter
import com.task.marvel.ui.dashboard.comicadapter.ComicListAdapter
import com.task.marvel.utils.DateUtils
import com.task.marvel.utils.Utils
import com.task.marvel.utils.base.BaseViewModel
import com.task.marvel.utils.base.sealed.UIEvent
import com.task.marvel.utils.uikit.pagination.PaginatedRecyclerView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardVM @Inject constructor(
    private val dataRepository: IDataRepository
) : BaseViewModel(), IDashboardVM {
    override var mCharacterPageNumber: Int = 0
    override val characterAdapter: CharacterListAdapter = CharacterListAdapter(mutableListOf())
    override var isFirstTimeCharacterFetching: Boolean = true

    override var mComicPageNumber: Int = 0

    override val comicAdapter: ComicListAdapter = ComicListAdapter(mutableListOf())
    override var isFirstTimeComicFetching: Boolean = true

    private val _uiState: MutableLiveData<UIEvent> = MutableLiveData()
    override val uiState: LiveData<UIEvent> = _uiState

    private val _uiComicState: MutableLiveData<UIEvent> = MutableLiveData()
    override val uiComicState: LiveData<UIEvent> = _uiComicState

    private val _heroLists: MutableLiveData<List<Character>> = MutableLiveData()
    override val heroLists: LiveData<List<Character>> = _heroLists

    private val _comicLists: MutableLiveData<List<Comic>> = MutableLiveData()
    override val comicLists: LiveData<List<Comic>> = _comicLists

    private val _totalHeroLists: MutableLiveData<List<Character>> = MutableLiveData()
    override val totalHeroLists: LiveData<List<Character>> = _totalHeroLists

    private val _totalComicLists: MutableLiveData<List<Comic>> = MutableLiveData()
    override val totalComicLists: LiveData<List<Comic>> = _totalComicLists

    private val _isCharactersCompleted: MutableLiveData<Boolean> = MutableLiveData()
    override val isCharactersCompleted: LiveData<Boolean> = _isCharactersCompleted

    private val _isComicsCompleted: MutableLiveData<Boolean> = MutableLiveData()
    override val isComicsCompleted: LiveData<Boolean> = _isComicsCompleted

    private val _isCharactersSuccess: MutableLiveData<Boolean> = MutableLiveData()
    override val isCharactersSuccess: LiveData<Boolean> = _isCharactersSuccess

    private val _isComicsSuccess: MutableLiveData<Boolean> = MutableLiveData()
    override val isComicsSuccess: LiveData<Boolean> = _isComicsSuccess

    override fun getCharacters(apiKey: String, hash: String, ts: String, offset: Int) {
        launch {
            if (isFirstTimeCharacterFetching)
                _uiState.postValue(UIEvent.Loading)
            val response = dataRepository.getMarvelCharacter(
                apiKey = apiKey,
                hash = hash,
                ts = ts,
                offset = offset
            )
            withContext(Dispatchers.Main) {
                when (response) {
                    is ApiResponse.Success -> {
                        _heroLists.value = response.data.results ?: listOf()
                        isFirstTimeCharacterFetching = false
                        _isCharactersSuccess.value = true
                        _isCharactersCompleted.value = response.data.count == 0
                        _uiState.value = UIEvent.Success
                    }
                    is ApiResponse.Error -> {
                        _isCharactersSuccess.value = false
                        _heroLists.value = listOf()
                        _uiState.value = UIEvent.Error(response.error.message)
                    }
                }
            }
        }
    }

    override fun getComics(apiKey: String, hash: String, ts: String, offset: Int) {
        launch {
            if (isFirstTimeComicFetching)
                _uiComicState.postValue(UIEvent.Loading)
            val response = dataRepository.getMarvelComics(
                apiKey = apiKey,
                hash = hash,
                ts = ts,
                offset = offset
            )
            withContext(Dispatchers.Main) {
                when (response) {
                    is ApiResponse.Success -> {
                        _comicLists.value = response.data.results ?: listOf()
                        isFirstTimeComicFetching = false
                        _isComicsSuccess.value = true
                        _isComicsCompleted.value = response.data.count == 0
                        _uiComicState.value = UIEvent.Success
                    }
                    is ApiResponse.Error -> {
                        _isComicsSuccess.value = false
                        _comicLists.value = listOf()
                        _uiComicState.value = UIEvent.Error(response.error.message)
                    }
                }
            }
        }
    }

    override fun updateTotalCharacterList(list: List<Character>) {
        val totalList: ArrayList<Character> = arrayListOf()
        totalList.addAll(_totalHeroLists.value ?: arrayListOf())
        totalList.addAll(list)
        _totalHeroLists.value = totalList

    }

    override fun updateTotalComicList(list: List<Comic>) {
        val totalList: ArrayList<Comic> = arrayListOf()
        totalList.addAll(_totalComicLists.value ?: arrayListOf())
        totalList.addAll(list)
        _totalComicLists.value = totalList
    }

    private val paginationCharacterListener = object : PaginatedRecyclerView.Pagination() {
        override fun onNextPage(page: Int) {
            val time = DateUtils.getCurrentTimeStamp()
            mCharacterPageNumber = page
            getCharacters(
                apiKey = BuildConfig.API_KEY,
                hash = Utils.getHash(BuildConfig.API_HASH, BuildConfig.API_KEY, time),
                offset = mCharacterPageNumber,
                ts = time
            )
        }
    }

    private val paginationComicListener = object : PaginatedRecyclerView.Pagination() {
        override fun onNextPage(page: Int) {
            val time = DateUtils.getCurrentTimeStamp()
            mComicPageNumber = page
            getComics(
                apiKey = BuildConfig.API_KEY,
                hash = Utils.getHash(BuildConfig.API_HASH, BuildConfig.API_KEY, time),
                offset = mComicPageNumber,
                ts = time
            )
        }
    }

    override fun getPaginationCharacterListener(): PaginatedRecyclerView.Pagination {
        return paginationCharacterListener
    }

    override fun getPaginationComicListener(): PaginatedRecyclerView.Pagination {
        return paginationComicListener
    }
}
