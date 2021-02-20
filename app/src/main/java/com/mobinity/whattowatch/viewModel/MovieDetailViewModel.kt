package com.mobinity.whattowatch.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.response.RemoteService
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MovieDetailViewModel {


    fun goBackBtn(view: View) {

        (view.context as Activity).finish()
        (view.context as Activity).overridePendingTransition(R.anim.fix, R.anim.slide_out_right)

    }

    fun getCourotineHandler(context: Context): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
            Toast.makeText(context, "조회중 오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    fun getRetrofitService(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(RemoteService.MOVIE_DB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}