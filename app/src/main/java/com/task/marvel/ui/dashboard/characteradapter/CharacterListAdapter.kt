package com.task.marvel.ui.dashboard.characteradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.task.marvel.R
import com.task.marvel.data.dtos.responsedtos.characters.Character
import com.task.marvel.databinding.LayoutItemCharacterBinding
import com.task.marvel.utils.base.BaseRecyclerAdapter

class CharacterListAdapter(
    private val list: MutableList<Character>,
) : BaseRecyclerAdapter<Character, CharacterListItemViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewBinding): CharacterListItemViewHolder {
        return CharacterListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterListItemViewHolder, position: Int) {
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
        return LayoutItemCharacterBinding.inflate(layoutInflater, viewGroup, false)
    }

    fun updateCharacterListItems(characterList: List<Character>) {
        val diffCallback = CharacterDiffCallback(list, characterList)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        this.setList(characterList)
        diffResult.dispatchUpdatesTo(this)
    }
}