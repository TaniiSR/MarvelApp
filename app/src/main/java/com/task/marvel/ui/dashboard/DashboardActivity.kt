package com.task.marvel.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.task.marvel.utils.extensions.gone
import com.task.marvel.utils.extensions.observe
import com.task.marvel.utils.extensions.visible
import com.task.marvel.BuildConfig
import com.task.marvel.R
import com.task.marvel.data.dtos.responsedtos.Character
import com.task.marvel.databinding.ActivityDashboardBinding
import com.task.marvel.utils.DateUtils
import com.task.marvel.utils.Utils
import com.task.marvel.utils.base.BaseActivity
import com.task.marvel.utils.base.interfaces.OnItemClickListener
import com.task.marvel.utils.base.sealed.UIEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : BaseActivity<ActivityDashboardBinding, IDashboardVM>() {
    override val viewModel: IDashboardVM by viewModels<DashboardVM>()
    override fun getViewBinding(): ActivityDashboardBinding =
        ActivityDashboardBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelObservers()
        setUpRecyclerView()
        setListener()
        val time = DateUtils.getCurrentTimeStamp()
        viewModel.getCharacters(
            apiKey = BuildConfig.API_KEY,
            hash = Utils.getHash(BuildConfig.API_HASH, BuildConfig.API_KEY, time),
            offset = 0,
            ts = time
        )
    }

    private fun setUpRecyclerView() {
        viewModel.characterAdapter.allowFullItemClickListener = true
        viewModel.characterAdapter.onItemClickListener = rvItemClickListener
        mViewBinding.rvCharactertList.adapter = viewModel.characterAdapter
        mViewBinding.rvCharactertList.threshold = 3
        mViewBinding.rvCharactertList.pagination = viewModel.getPaginationCharacterListener()
        if (viewModel.isFirstTimeCharacterFetching)
            mViewBinding.rvCardsTransactions.pagination?.notifyPaginationRestart()
    }

    private val rvItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (data) {
                is Character -> {
                    startDetailScreen(data)
                }
            }
        }
    }

    private fun startDetailScreen(data: Character) {
//        val intent = Intent(this, DetailActivity::class.java)
//        intent.putExtra(DetailActivity::class.java.name, data.login)
//        startActivity(intent)
    }

    private fun handleHeroList(list: List<Character>) {
        viewModel.updateTotalCharacterList(list)
    }

    private fun handleTotalHeroList(list: List<Character>) {
        viewModel.characterAdapter.updateCharacterListItems(list)
    }

    private fun setListener() {
        mViewBinding.errorView.btnRetry.setOnClickListener(this)
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnRetry -> Unit
        }
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
        hideShimmerLoading()
    }

    private fun setSuccessView() {
        mViewBinding.rvCharactertList.visible()
        mViewBinding.errorView.errorView.gone()
        hideShimmerLoading()
    }

    private fun setLoadingView() {
        mViewBinding.errorView.errorView.gone()
        mViewBinding.rvCharactertList.gone()
        showShimmerLoading()
    }

    private fun showShimmerLoading() {
        mViewBinding.shimmerLayout.shimmerFrameLayout.visible()
        mViewBinding.shimmerLayout.shimmerFrameLayout.startShimmer()

    }

    private fun hideShimmerLoading() {
        mViewBinding.shimmerLayout.shimmerFrameLayout.gone()
        mViewBinding.shimmerLayout.shimmerFrameLayout.stopShimmer()
    }

    private fun handleCharacterSuccess(isSuccess: Boolean) {
        if (isSuccess) viewModel.getPaginationCharacterListener().notifyPageLoaded() else
            viewModel.getPaginationCharacterListener().notifyPageError()
    }

    private fun handleCharacterCompleted(isCompleted: Boolean) {
        if (isCompleted) viewModel.getPaginationCharacterListener().notifyPaginationCompleted()
    }

    private fun viewModelObservers() {
        observe(viewModel.heroLists, ::handleHeroList)
        observe(viewModel.totalHeroLists, ::handleTotalHeroList)
        observe(viewModel.uiState, ::handleUiState)
        observe(viewModel.isCharactersSuccess, ::handleCharacterSuccess)
        observe(viewModel.isCharactersCompleted, ::handleCharacterCompleted)
    }

}
