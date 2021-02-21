package com.mobinity.whattowatch.util

import android.app.Activity
import android.widget.Toast
import kotlin.system.exitProcess

class BackPressCloseHandler(val activity: Activity?) {

    private var backKeyPressedTime: Long = 0
    private lateinit var myToast: Toast


    fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            showGuide()
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity!!.finish()
            myToast.cancel()

        }
    }

    private fun showGuide() {
        myToast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
        myToast.show()
    }

}