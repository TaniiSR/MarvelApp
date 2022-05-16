package com.task.marvel.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.task.marvel.BuildConfig
import com.task.marvel.R
import com.task.marvel.data.dtos.responsedtos.characters.Character
import com.task.marvel.data.dtos.responsedtos.comics.Comic
import com.task.marvel.databinding.ActivityDashboardBinding
import com.task.marvel.utils.DateUtils
import com.task.marvel.utils.Utils
import com.task.marvel.utils.base.BaseActivity
import com.task.marvel.utils.base.interfaces.OnItemClickListener
import com.task.marvel.utils.base.sealed.UIEvent
import com.task.marvel.utils.extensions.gone
import com.task.marvel.utils.extensions.observe
import com.task.marvel.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : BaseActivity<ActivityDashboardBinding, IDashboardVM>() {
    override val viewModel: IDashboardVM by viewModels<DashboardVM>()
    override fun getViewBinding(): ActivityDashboardBinding =
        ActivityDashboardBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelObservers()
        setUpCharacterRecyclerView()
        setUpComicRecyclerView()
        setListener()
    }

    private fun setUpCharacterRecyclerView() {
        viewModel.characterAdapter.allowFullItemClickListener = true
        viewModel.characterAdapter.onItemClickListener = rvItemClickListener
        mViewBinding.rvCharactertList.adapter = viewModel.characterAdapter
        mViewBinding.rvCharactertList.threshold = 3
        mViewBinding.rvCharactertList.pagination = viewModel.getPaginationCharacterListener()
        if (viewModel.isFirstTimeCharacterFetching)
            mViewBinding.rvCharactertList.pagination?.notifyPaginationRestart()
    }

    private fun setUpComicRecyclerView() {
        viewModel.comicAdapter.allowFullItemClickListener = true
        viewModel.comicAdapter.onItemClickListener = rvItemClickListener
        mViewBinding.rvComics.adapter = viewModel.comicAdapter
        mViewBinding.rvComics.threshold = 3
        mViewBinding.rvComics.pagination = viewModel.getPaginationComicListener()
        if (viewModel.isFirstTimeComicFetching)
            mViewBinding.rvComics.pagination?.notifyPaginationRestart()
    }

    private val rvItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (data) {
                is Character -> {
                    startDetailScreen(data)
                }
                is Comic -> Unit
            }
        }
    }

    private fun startDetailScreen(data: Character) {
//        val intent = Intent(this, DetailActivity::class.java)
//        intent.putExtra(DetailActivity::class.java.name, data.login)
//        startActivity(intent)
    }

    private fun handleComicList(list: List<Comic>) {
        viewModel.updateTotalComicList(list)
    }

    private fun handleHeroList(list: List<Character>) {
        viewModel.updateTotalCharacterList(list)
    }

    private fun handleTotalHeroList(list: List<Character>) {
        viewModel.characterAdapter.updateCharacterListItems(list)
    }

    private fun handleTotalComicList(list: List<Comic>) {
        viewModel.comicAdapter.updateCharacterListItems(list)
    }

    private fun setListener() {
        mViewBinding.errorView.btnRetry.setOnClickListener(this)
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnRetry -> {
                val time = DateUtils.getCurrentTimeStamp()
                viewModel.getComics(
                    apiKey = BuildConfig.API_KEY,
                    hash = Utils.getHash(BuildConfig.API_HASH, BuildConfig.API_KEY, time),
                    offset = viewModel.mComicPageNumber,
                    ts = time
                )
                viewModel.getCharacters(
                    apiKey = BuildConfig.API_KEY,
                    hash = Utils.getHash(BuildConfig.API_HASH, BuildConfig.API_KEY, time),
                    offset = viewModel.mCharacterPageNumber,
                    ts = time
                )
            }
        }
    }

    private fun handleUiComicState(uiEvent: UIEvent) {
        when (uiEvent) {
            is UIEvent.Loading -> setComicLoadingView()
            is UIEvent.Success -> setComicSuccessView()
            is UIEvent.Error -> setErrorView()
            is UIEvent.Message -> showToast(uiEvent.message)
        }
    }

    private fun setComicSuccessView() {
        mViewBinding.rvComics.visible()
        mViewBinding.errorView.errorView.gone()
        hideComicShimmerLoading()
    }

    private fun setComicLoadingView() {
        mViewBinding.errorView.errorView.gone()
        mViewBinding.rvComics.gone()
        showComicShimmerLoading()
    }

    private fun handleUiState(uiEvent: UIEvent) {
        when (uiEvent) {
            is UIEvent.Loading -> setLoadingView()
            is UIEvent.Success -> setSuccessView()
            is UIEvent.Error -> setErrorView()
            is UIEvent.Message -> showToast(uiEvent.message)
        }
    }

    private fun setErrorView() {
        mViewBinding.errorView.errorView.visible()
        mViewBinding.rvCharactertList.gone()
        mViewBinding.rvComics.gone()
        hideComicShimmerLoading()
        hideCharacterShimmerLoading()
    }

    private fun setSuccessView() {
        mViewBinding.rvCharactertList.visible()
        mViewBinding.errorView.errorView.gone()
        hideCharacterShimmerLoading()
    }

    private fun setLoadingView() {
        mViewBinding.errorView.errorView.gone()
        mViewBinding.rvCharactertList.gone()
        showCharacterShimmerLoading()
    }

    private fun showComicShimmerLoading() {
        mViewBinding.shimmerLayout.shimmerFrameLayout.visible()
        mViewBinding.shimmerLayout.shimmerFrameLayout.startShimmer()

    }

    private fun hideComicShimmerLoading() {
        mViewBinding.shimmerLayout.shimmerFrameLayout.gone()
        mViewBinding.shimmerLayout.shimmerFrameLayout.stopShimmer()
    }

    private fun showCharacterShimmerLoading() {
        mViewBinding.shimmerCharacter.shimmerFrameLayout.visible()
        mViewBinding.shimmerCharacter.shimmerFrameLayout.startShimmer()

    }

    private fun hideCharacterShimmerLoading() {
        mViewBinding.shimmerCharacter.shimmerFrameLayout.gone()
        mViewBinding.shimmerCharacter.shimmerFrameLayout.stopShimmer()
    }

    private fun handleCharacterSuccess(isSuccess: Boolean) {
        if (isSuccess) viewModel.getPaginationCharacterListener().notifyPageLoaded() else
            viewModel.getPaginationCharacterListener().notifyPageError()
    }

    private fun handleComicSuccess(isSuccess: Boolean) {
        if (isSuccess) viewModel.getPaginationComicListener().notifyPageLoaded() else
            viewModel.getPaginationComicListener().notifyPageError()
    }

    private fun handleCharacterCompleted(isCompleted: Boolean) {
        if (isCompleted) viewModel.getPaginationCharacterListener().notifyPaginationCompleted()
    }

    private fun handleComicCompleted(isCompleted: Boolean) {
        if (isCompleted) viewModel.getPaginationComicListener().notifyPaginationCompleted()
    }

    private fun viewModelObservers() {
        observe(viewModel.heroLists, ::handleHeroList)
        observe(viewModel.comicLists, ::handleComicList)
        observe(viewModel.totalHeroLists, ::handleTotalHeroList)
        observe(viewModel.totalComicLists, ::handleTotalComicList)
        observe(viewModel.uiState, ::handleUiState)
        observe(viewModel.uiComicState, ::handleUiComicState)
        observe(viewModel.isCharactersSuccess, ::handleCharacterSuccess)
        observe(viewModel.isComicsSuccess, ::handleComicSuccess)
        observe(viewModel.isCharactersCompleted, ::handleCharacterCompleted)
        observe(viewModel.isComicsCompleted, ::handleComicCompleted)
    }

}
