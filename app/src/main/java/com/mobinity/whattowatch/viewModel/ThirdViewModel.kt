package com.mobinity.whattowatch.viewModel

import android.app.Activity
import android.content.Intent
import android.view.View
import com.mobinity.whattowatch.MyApplication
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.view.FourthActivity

class ThirdViewModel {

    fun selectQuestion3(answer: String, view: View) {


        when (answer) {

            view.context.getString(R.string.dont_care) -> {
                MyApplication.answer3 = view.context.getString(R.string.dont_care)
            }

            view.context.getString(R.string.date2020) -> {
                MyApplication.answer3 = view.context.getString(R.string.date2020)
            }

            view.context.getString(R.string.date2015) -> {
                MyApplication.answer3 = view.context.getString(R.string.date2015)
            }

            view.context.getString(R.string.date2010) -> {
                MyApplication.answer3 = view.context.getString(R.string.date2010)
            }

            view.context.getString(R.string.date2005) -> {
                MyApplication.answer3 = view.context.getString(R.string.date2005)
            }

            view.context.getString(R.string.date2000) -> {
                MyApplication.answer3 = view.context.getString(R.string.date2000)
            }

            view.context.getString(R.string.date2000before) -> {
                MyApplication.answer3 = view.context.getString(R.string.date2000before)
            }

        }

        var intent = Intent(view.context, FourthActivity::class.java)
        view.context.startActivity(intent)
        (view.context as Activity).overridePendingTransition(R.anim.slide_in_right, R.anim.fix)

    }

    fun goBackBtn(view: View){

        (view.context as Activity).finish()
        (view.context as Activity).overridePendingTransition(R.anim.fix, R.anim.slide_out_right)

    }

}