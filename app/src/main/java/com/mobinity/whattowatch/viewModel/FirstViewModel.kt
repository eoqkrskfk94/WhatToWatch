package com.mobinity.whattowatch.viewModel

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
                Toast.makeText(view.context, "dont care", Toast.LENGTH_SHORT).show()
                MyApplication.answer1 = view.context.getString(R.string.dont_care)
            }

            view.context.getString(R.string.action) -> {
                Toast.makeText(view.context, "action", Toast.LENGTH_SHORT).show()
                MyApplication.answer1 = view.context.getString(R.string.action)
            }

        }

        var intent = Intent(view.context, SecondActivity::class.java)
        view.context.startActivity(intent)

    }

}