package com.task.marvel.ui.dashboard.comicadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.task.marvel.R
import com.task.marvel.data.dtos.responsedtos.comics.Comic
import com.task.marvel.databinding.LayoutItemComicListViewBinding
import com.task.marvel.utils.base.BaseRecyclerAdapter

class ComicListAdapter(
    private val list: MutableList<Comic>,
) : BaseRecyclerAdapter<Comic, ComicListItemViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewBinding): ComicListItemViewHolder {
        return ComicListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComicListItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(list[position], position, onItemClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.layout_item_character
    }

    override fun getItemCount(): Int = list.size

    override fun getViewBindingByViewType(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewBinding {
        return LayoutItemComicListViewBinding.inflate(layoutInflater, viewGroup, false)
    }

    fun updateCharacterListItems(comicList: List<Comic>) {
        val diffCallback = ComicDiffCallback(list, comicList)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        this.setList(comicList)
        diffResult.dispatchUpdatesTo(this)
    }
}