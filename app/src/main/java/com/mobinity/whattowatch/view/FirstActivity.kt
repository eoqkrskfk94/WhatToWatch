package com.mobinity.whattowatch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.databinding.ActivityFirstBinding
import com.mobinity.whattowatch.util.BackPressCloseHandler
import com.mobinity.whattowatch.viewModel.FirstViewModel
import kotlinx.coroutines.Job

class FirstActivity : AppCompatActivity() {

    private lateinit var job: Job
    private lateinit var backPressCloseHandler: BackPressCloseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding:ActivityFirstBinding = DataBindingUtil.setContentView(this, R.layout.activity_first)
        binding.lifecycleOwner = this
        binding.viewModel = FirstViewModel()
        setTextAnimation(binding)

        backPressCloseHandler =
            BackPressCloseHandler(this)

    }


    private fun setTextAnimation(binding: ActivityFirstBinding){
        val fadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)
        val fadeIn2 = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)
        fadeIn2.duration = 2000

        binding.tvCurrentQuestion.startAnimation(fadeIn)
        binding.tvQuestion1.startAnimation(fadeIn)

        binding.tvQuestion11.startAnimation(fadeIn2)
        binding.tvQuestion12.startAnimation(fadeIn2)
        binding.tvQuestion13.startAnimation(fadeIn2)
        binding.tvQuestion14.startAnimation(fadeIn2)
        binding.tvQuestion15.startAnimation(fadeIn2)
        binding.tvQuestion16.startAnimation(fadeIn2)
        binding.tvQuestion17.startAnimation(fadeIn2)
        binding.tvQuestion18.startAnimation(fadeIn2)
        binding.tvQuestion19.startAnimation(fadeIn2)
        binding.tvQuestion110.startAnimation(fadeIn2)
        binding.tvQuestion111.startAnimation(fadeIn2)
        binding.tvQuestion112.startAnimation(fadeIn2)
        binding.tvQuestion113.startAnimation(fadeIn2)
        binding.tvQuestion114.startAnimation(fadeIn2)

    }

    override fun onBackPressed() {
        backPressCloseHandler.onBackPressed()
    }

}