package com.mobinity.whattowatch.viewModel

import android.app.Activity
import android.content.Intent
import android.view.View
import com.mobinity.whattowatch.R


class MovieDetailViewModel {


    fun goBackBtn(view: View) {

        (view.context as Activity).finish()
        (view.context as Activity).overridePendingTransition(R.anim.fix, R.anim.slide_out_right)


    }
}