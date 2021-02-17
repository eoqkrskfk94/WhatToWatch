package com.mobinity.whattowatch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.databinding.ActivityLanguageSelectBinding
import com.mobinity.whattowatch.util.BackPressCloseHandler
import com.mobinity.whattowatch.viewModel.LanguageSelectViewModel

class LanguageSelectActivity : AppCompatActivity() {
    private lateinit var backPressCloseHandler: BackPressCloseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLanguageSelectBinding = DataBindingUtil.setContentView(this, R.layout.activity_language_select)
        binding.lifecycleOwner = this
        binding.viewModel = LanguageSelectViewModel()

        setTextAnimation(binding)

        backPressCloseHandler =
            BackPressCloseHandler(this)
    }


    private fun setTextAnimation(binding: ActivityLanguageSelectBinding){
        val fadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)
        val fadeIn2 = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)
        fadeIn2.duration = 2000

        binding.tvLanguageSelect.startAnimation(fadeIn)

        binding.btnLanguageEnglish.startAnimation(fadeIn2)
        binding.btnLanguageKorean.startAnimation(fadeIn2)


    }

    override fun onBackPressed() {
        backPressCloseHandler.onBackPressed()
    }
}