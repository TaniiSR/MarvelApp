package com.task.marvel.utils.base.interfaces

import com.task.marvel.utils.base.SingleClickEvent

interface IBaseViewModel {
    val clickEvent: SingleClickEvent
    fun onClick(view: android.view.View)
}