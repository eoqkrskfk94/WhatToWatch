package com.mobinity.whattowatch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.databinding.ActivitySecondBinding
import com.mobinity.whattowatch.viewModel.SecondViewModel

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySecondBinding = DataBindingUtil.setContentView(this, R.layout.activity_second)
        binding.lifecycleOwner = this
        binding.viewModel = SecondViewModel()
        setTextAnimation(binding)


    }

    private fun setTextAnimation(binding: ActivitySecondBinding){
        val fadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)
        val fadeIn2 = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)
        fadeIn.duration = 2000
        fadeIn2.duration = 2000

        binding.tvCurrentQuestion.startAnimation(fadeIn)
        binding.tvQuestion2.startAnimation(fadeIn)

        binding.tvQuestion21.startAnimation(fadeIn2)
        binding.tvQuestion22.startAnimation(fadeIn2)
        binding.tvQuestion23.startAnimation(fadeIn2)
        binding.tvQuestion24.startAnimation(fadeIn2)
        binding.tvQuestion25.startAnimation(fadeIn2)
        binding.tvQuestion26.startAnimation(fadeIn2)
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fix, R.anim.slide_out_right)
    }
}