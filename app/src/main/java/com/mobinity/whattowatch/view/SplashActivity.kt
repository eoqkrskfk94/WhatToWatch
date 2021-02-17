package com.mobinity.whattowatch.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobinity.whattowatch.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPreferences = getSharedPreferences("LANGUAGE_SELECT", MODE_PRIVATE)
        var language = sharedPreferences.getInt("LANGUAGE", 0)

        println(language)

        if(language == 0){
            val intent = Intent(this, LanguageSelectActivity::class.java)
            startActivity(intent)
        }
        else if(language == 1){
            val intent = Intent(this, FirstActivity::class.java)
            startActivity(intent)
        }
        else if(language == 2){
            val intent = Intent(this, FirstActivity::class.java)
            startActivity(intent)
        }


    }
}