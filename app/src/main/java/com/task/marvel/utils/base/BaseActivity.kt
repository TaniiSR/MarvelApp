package com.task.marvel.utils.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.task.marvel.utils.extensions.observe
import com.task.marvel.utils.extensions.toast
import com.task.marvel.utils.base.interfaces.IBaseViewModel


abstract class BaseActivity<VB : ViewBinding, VM : IBaseViewModel> :
    AppCompatActivity(), View.OnClickListener {

    lateinit var mViewBinding: VB
    abstract fun getViewBinding(): VB
    abstract val viewModel: VM

    open fun onClick(id: Int) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = getViewBinding()
        setContentView(mViewBinding.root)
        setObservers()
    }

    override fun onClick(view: View) {
        viewModel.onClick(view)
    }

    private fun setObservers() {
        observe(viewModel.clickEvent, ::handleClickEvent)
    }

    private fun handleClickEvent(clickEventId: Int) {
        onClick(clickEventId)
    }

    fun showToast(msg: String) {
        if (msg.isNotBlank()) {
            toast(msg)
        }
    }

}