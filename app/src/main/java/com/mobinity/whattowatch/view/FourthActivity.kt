package com.mobinity.whattowatch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.databinding.ActivityFourthBinding
import com.mobinity.whattowatch.databinding.ActivityThirdBinding
import com.mobinity.whattowatch.viewModel.FourthViewModel

class FourthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityFourthBinding = DataBindingUtil.setContentView(this, R.layout.activity_fourth)
        binding.lifecycleOwner = this
        binding.viewModel = FourthViewModel()


        setTextAnimation(binding)
    }

    private fun setTextAnimation(binding: ActivityFourthBinding){
        val fadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)
        val fadeIn2 = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)
        fadeIn.duration = 2000
        fadeIn2.duration = 2000

        binding.tvCurrentQuestion.startAnimation(fadeIn)
        binding.tvQuestion4.startAnimation(fadeIn)

        binding.tvQuestion41.startAnimation(fadeIn2)
        binding.tvQuestion42.startAnimation(fadeIn2)
        binding.tvQuestion43.startAnimation(fadeIn2)
        binding.tvQuestion44.startAnimation(fadeIn2)
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fix, R.anim.slide_out_right)
    }
}