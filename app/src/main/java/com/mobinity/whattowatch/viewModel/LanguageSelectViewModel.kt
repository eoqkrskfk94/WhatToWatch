package com.mobinity.whattowatch.viewModel

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.view.FirstActivity

class LanguageSelectViewModel {


    fun languageBtn(answer: String, view: View) {


        val sharedPreferences = (view.context as Activity).getSharedPreferences("LANGUAGE_SELECT", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()



        when (answer) {

            view.context.getString(R.string.english_string) -> {
                editor.putInt("LANGUAGE", 1)
                editor.commit()
            }

            view.context.getString(R.string.korean_string) -> {
                editor.putInt("LANGUAGE", 2)
                editor.commit()
            }

        }


        var intent = Intent(view.context, FirstActivity::class.java)
        view.context.startActivity(intent)
        (view.context as Activity).overridePendingTransition(R.anim.slide_in_right, R.anim.fix)

    }


}