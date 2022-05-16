package com.task.marvel.ui.dashboard.comicadapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.task.marvel.data.dtos.responsedtos.characters.Character
import com.task.marvel.data.dtos.responsedtos.comics.Comic
import com.task.marvel.databinding.LayoutItemComicListViewBinding
import com.task.marvel.utils.base.interfaces.OnItemClickListener
import com.task.marvel.utils.base.sealed.ImageSize
import com.task.marvel.utils.extensions.loadImage

class ComicListItemViewHolder(private val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun onBind(
        data: Comic,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        when (binding) {
            is LayoutItemComicListViewBinding -> {
                binding.ivImage.loadImage(data.thumbnail?.path + ImageSize.Medium().name + data.thumbnail?.extension)
                binding.tvDescription.text = data.description
            }
        }
    }
}