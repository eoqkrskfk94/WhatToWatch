package com.mobinity.whattowatch.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.mobinity.whattowatch.MyApplication
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.response.RemoteService
import com.mobinity.whattowatch.view.FirstActivity
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class FifthViewModel {


    fun goBackBtn(view: View){

        var intent = Intent(view.context, FirstActivity::class.java)
        view.context.startActivity(intent)
        (view.context as Activity).finish()
        (view.context as Activity).overridePendingTransition(R.anim.fix, R.anim.slide_out_right)

    }

    fun getRetrofitService(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(RemoteService.MOVIE_DB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getCourotineHandler(context: Context): CoroutineExceptionHandler{
        return CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
            Toast.makeText(context, "조회중 오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    fun getRandomYear(context: Context): String?{

        val rand = Random(System.nanoTime())

        when (MyApplication.answer3) {

            context.getString(R.string.dont_care) -> {
                return null
            }

            context.getString(R.string.date2020) -> {
                return (2020..2021).random(rand).toString()
            }

            context.getString(R.string.date2015) -> {
                return (2015..2019).random(rand).toString()
            }

            context.getString(R.string.date2010) -> {
                return (2010..2014).random(rand).toString()
            }

            context.getString(R.string.date2005) -> {
                return (2005..2009).random(rand).toString()
            }

            context.getString(R.string.date2000) -> {
                return (2000..2004).random(rand).toString()
            }

            context.getString(R.string.date2000before) -> {
                return (1990..1999).random(rand).toString()
            }

            else -> return null

        }

    }


}