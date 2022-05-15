package com.task.marvel.ui.splash

import android.animation.Animator
import android.os.Bundle
import androidx.activity.viewModels
import com.task.marvel.BuildConfig
import com.task.marvel.databinding.ActivitySplashBinding
import com.task.marvel.utils.DateUtils
import com.task.marvel.utils.Utils.getHash
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
        val time = DateUtils.getCurrentTimeStamp()
        viewModel.getCharacter(
            apiKey = BuildConfig.API_KEY,
            hashKey = getHash(BuildConfig.API_HASH, BuildConfig.API_KEY, time),
            time = time,
            offset = 0
        )
//        finish()
    }

    override fun onAnimationCancel(p0: Animator?) = Unit

    override fun onAnimationRepeat(p0: Animator?) = Unit
}