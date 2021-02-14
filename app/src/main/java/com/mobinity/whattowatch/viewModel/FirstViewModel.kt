package com.mobinity.whattowatch.viewModel

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.mobinity.whattowatch.MyApplication
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.view.SecondActivity

class FirstViewModel {

    fun selectQuestion1(answer : String, view : View){



        when(answer){

            view.context.getString(R.string.dont_care) -> {
                MyApplication.answer1 = null
            }

            view.context.getString(R.string.action) -> {
                MyApplication.answer1 = view.context.getString(R.string.action)
            }

            view.context.getString(R.string.sf) -> {
                MyApplication.answer1 = view.context.getString(R.string.sf)
            }

            view.context.getString(R.string.drama) -> {
                MyApplication.answer1 = view.context.getString(R.string.drama)
            }

            view.context.getString(R.string.thriller) -> {
                MyApplication.answer1 = view.context.getString(R.string.thriller)
            }

            view.context.getString(R.string.animation) -> {
                MyApplication.answer1 = view.context.getString(R.string.animation)
            }

            view.context.getString(R.string.horror) -> {
                MyApplication.answer1 = view.context.getString(R.string.horror)
            }

            view.context.getString(R.string.fantasy) -> {
                MyApplication.answer1 = view.context.getString(R.string.fantasy)
            }

            view.context.getString(R.string.romance) -> {
                MyApplication.answer1 = view.context.getString(R.string.romance)
            }

            view.context.getString(R.string.mystery) -> {
                MyApplication.answer1 = view.context.getString(R.string.mystery)
            }

            view.context.getString(R.string.family) -> {
                MyApplication.answer1 = view.context.getString(R.string.family)
            }

            view.context.getString(R.string.music) -> {
                MyApplication.answer1 = view.context.getString(R.string.music)
            }

            view.context.getString(R.string.comedy) -> {
                MyApplication.answer1 = view.context.getString(R.string.comedy)
            }

            view.context.getString(R.string.documentary) -> {
                MyApplication.answer1 = view.context.getString(R.string.documentary)
            }

        }

        var intent = Intent(view.context, SecondActivity::class.java)
        view.context.startActivity(intent)
        (view.context as Activity).overridePendingTransition(R.anim.slide_in_right, R.anim.fix)

    }

}