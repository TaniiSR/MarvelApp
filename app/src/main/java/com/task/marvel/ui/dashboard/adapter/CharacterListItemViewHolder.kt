package com.task.marvel.ui.dashboard.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.task.marvel.utils.extensions.loadImage
import com.task.marvel.data.dtos.responsedtos.Character
import com.task.marvel.databinding.LayoutItemCharacterBinding
import com.task.marvel.utils.base.interfaces.OnItemClickListener
import com.task.marvel.utils.base.sealed.ImageSize

class CharacterListItemViewHolder(private val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun onBind(
        data: Character,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        when (binding) {
            is LayoutItemCharacterBinding -> {
                binding.ivIcon.loadImage(data.thumbnail?.path+ ImageSize.Medium().name+ data.thumbnail?.extension)
                binding.tvTitle.text = data.name
            }
        }
    }
}