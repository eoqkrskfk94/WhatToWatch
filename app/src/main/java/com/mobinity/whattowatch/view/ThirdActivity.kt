package com.mobinity.whattowatch.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.databinding.ActivityThirdBinding
import com.mobinity.whattowatch.util.BackPressCloseHandler
import com.mobinity.whattowatch.viewModel.ThirdViewModel

class ThirdActivity : AppCompatActivity() {
    private lateinit var backPressCloseHandler: BackPressCloseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityThirdBinding = DataBindingUtil.setContentView(this, R.layout.activity_third)
        binding.lifecycleOwner = this
        binding.viewModel = ThirdViewModel()
        setTextAnimation(binding)

        backPressCloseHandler = BackPressCloseHandler(this)
    }

    private fun setTextAnimation(binding: ActivityThirdBinding){
        val fadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)
        val fadeIn2 = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)
        fadeIn.duration = 2000
        fadeIn2.duration = 2000

        binding.tvCurrentQuestion.startAnimation(fadeIn)
        binding.tvQuestion3.startAnimation(fadeIn)

        binding.tvQuestion31.startAnimation(fadeIn2)
        binding.tvQuestion32.startAnimation(fadeIn2)
        binding.tvQuestion33.startAnimation(fadeIn2)
        binding.tvQuestion34.startAnimation(fadeIn2)
        binding.tvQuestion35.startAnimation(fadeIn2)
        binding.tvQuestion36.startAnimation(fadeIn2)
        binding.tvQuestion37.startAnimation(fadeIn2)
    }


//    override fun finish() {
//        super.finish()
//        overridePendingTransition(R.anim.fix, R.anim.slide_out_right)
//    }

    override fun onBackPressed() {
        backPressCloseHandler.onBackPressed()
    }


}