package com.task.marvel.ui.splash

import android.animation.Animator
import android.os.Bundle
import androidx.activity.viewModels
import com.task.marvel.databinding.ActivitySplashBinding
import com.task.marvel.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, ISplashVM>(), Animator.AnimatorListener {
    override val viewModel: ISplashVM by viewModels<SplashVM>()
    override fun getViewBinding(): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding.lottieAnimation.addAnimatorListener(this)
    }

    override fun onAnimationStart(p0: Animator?) = Unit

    override fun onAnimationEnd(p0: Animator?) {
        finish()
    }

    override fun onAnimationCancel(p0: Animator?) = Unit

    override fun onAnimationRepeat(p0: Animator?) = Unit
}