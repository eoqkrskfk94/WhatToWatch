package com.mobinity.whattowatch.viewModel

import android.app.Activity
import android.content.Intent
import android.view.View
import com.mobinity.whattowatch.MyApplication
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.view.FifthActivity
import com.mobinity.whattowatch.view.FirstActivity
import com.mobinity.whattowatch.view.ThirdActivity

class FourthViewModel {

    fun selectQuestion4(answer: String, view: View) {

        when (answer) {

            view.context.getString(R.string.dont_care) -> {
                MyApplication.answer4 = null
            }

            view.context.getString(R.string.netflix) -> {
                MyApplication.answer4 = view.context.getString(R.string.netflix)
            }

            view.context.getString(R.string.watcha) -> {
                MyApplication.answer4 = view.context.getString(R.string.watcha)
            }

            view.context.getString(R.string.wavve) -> {
                MyApplication.answer4 = view.context.getString(R.string.wavve)
            }


        }

        var intent = Intent(view.context, FifthActivity::class.java)
        view.context.startActivity(intent)
        (view.context as Activity).finish()
        (view.context as Activity).overridePendingTransition(R.anim.slide_in_right, R.anim.fix)

    }

    fun goBackBtn(view: View){

        var intent = Intent(view.context, ThirdActivity::class.java)
        view.context.startActivity(intent)
        (view.context as Activity).finish()
        (view.context as Activity).overridePendingTransition(R.anim.fix, R.anim.slide_out_right)

    }

}