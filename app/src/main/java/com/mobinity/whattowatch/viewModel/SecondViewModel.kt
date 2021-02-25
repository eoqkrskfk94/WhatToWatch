package com.mobinity.whattowatch.viewModel

import android.app.Activity
import android.content.Intent
import android.view.View
import com.mobinity.whattowatch.MyApplication
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.view.FirstActivity
import com.mobinity.whattowatch.view.ThirdActivity

class SecondViewModel {

    fun selectQuestion2(answer: String, view: View) {


        when (answer) {

            view.context.getString(R.string.dont_care) -> {
                MyApplication.answer2 = null
            }

            view.context.getString(R.string.korea) -> {
                MyApplication.answer2 = view.context.getString(R.string.korea)
            }

            view.context.getString(R.string.english) -> {
                MyApplication.answer2 = view.context.getString(R.string.english)
            }

            view.context.getString(R.string.japan) -> {
                MyApplication.answer2 = view.context.getString(R.string.japan)
            }

            view.context.getString(R.string.france) -> {
                MyApplication.answer2 = view.context.getString(R.string.france)
            }

            view.context.getString(R.string.china) -> {
                MyApplication.answer2 = view.context.getString(R.string.china)
            }

        }

        var intent = Intent(view.context, ThirdActivity::class.java)
        view.context.startActivity(intent)
        (view.context as Activity).finish()
        (view.context as Activity).overridePendingTransition(R.anim.slide_in_right, R.anim.fix)

    }

    fun goBackBtn(view: View){

        var intent = Intent(view.context, FirstActivity::class.java)
        view.context.startActivity(intent)
        (view.context as Activity).finish()
        (view.context as Activity).overridePendingTransition(R.anim.fix, R.anim.slide_out_right)

    }

}

