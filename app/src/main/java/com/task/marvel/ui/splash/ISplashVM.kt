package com.task.marvel.ui.splash

import com.task.marvel.utils.base.interfaces.IBaseViewModel

interface ISplashVM : IBaseViewModel {
    fun getCharacter(apiKey: String, hashKey: String, time: String, offset: Int)
}