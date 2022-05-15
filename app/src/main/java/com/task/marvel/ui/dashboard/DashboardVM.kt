package com.task.marvel.ui.dashboard


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.marvel.BuildConfig
import com.task.marvel.data.dtos.responsedtos.Character
import com.task.marvel.data.remote.baseclient.ApiResponse
import com.task.marvel.domain.IDataRepository
import com.task.marvel.ui.dashboard.adapter.CharacterListAdapter
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

    private val _uiState: MutableLiveData<UIEvent> = MutableLiveData()
    override val uiState: LiveData<UIEvent> = _uiState

    private val _heroLists: MutableLiveData<List<Character>> = MutableLiveData()
    override val heroLists: LiveData<List<Character>> = _heroLists

    private val _totalHeroLists: MutableLiveData<List<Character>> = MutableLiveData()
    override val totalHeroLists: LiveData<List<Character>> = _totalHeroLists

    private val _isCharactersCompleted: MutableLiveData<Boolean> = MutableLiveData()
    override val isCharactersCompleted: LiveData<Boolean> = _isCharactersCompleted

    private val _isCharactersSuccess: MutableLiveData<Boolean> = MutableLiveData()
    override val isCharactersSuccess: LiveData<Boolean> = _isCharactersSuccess

    override fun getCharacters(apiKey: String, hash: String, ts: String, offset: Int) {
        launch {
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

    override fun updateTotalCharacterList(list: List<Character>) {
        val totalList: ArrayList<Character> = arrayListOf()
        totalList.addAll(_totalHeroLists.value ?: arrayListOf())
        totalList.addAll(list)
        _totalHeroLists.value = totalList

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

    override fun getPaginationCharacterListener(): PaginatedRecyclerView.Pagination {
        return paginationCharacterListener
    }
}
