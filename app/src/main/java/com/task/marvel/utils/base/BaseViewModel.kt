package com.task.marvel.utils.base

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.marvel.utils.base.interfaces.IBaseViewModel
import kotlinx.coroutines.*


abstract class BaseViewModel : ViewModel(), IBaseViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    fun launch(dispatcher: CoroutineDispatcher = Dispatchers.IO, block: suspend () -> Unit): Job {
        return viewModelScope.launch(dispatcher) { block() }
    }

    fun <T> launchAsync(block: suspend () -> T): Deferred<T> =
        viewModelScope.async(Dispatchers.IO) {
            block()
        }

    override fun onClick(view: View) {
        clickEvent.setValue(view.id)
    }

}