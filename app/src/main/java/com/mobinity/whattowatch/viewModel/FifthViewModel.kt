package com.mobinity.whattowatch.viewModel

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.Toast
import com.mobinity.whattowatch.MyApplication
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.response.RemoteService
import com.mobinity.whattowatch.view.FirstActivity
import com.mobinity.whattowatch.view.MovieDetailActivity
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class FifthViewModel {

    fun moviePosterClick(view: View, movieId: Int, moviePoster: String) {

        var intent = Intent(view.context, MovieDetailActivity::class.java)
        intent.putExtra("movieId", movieId)
        intent.putExtra("moviePoster", moviePoster)
        view.context.startActivity(intent)

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            val activityOptions = ActivityOptions.makeSceneTransitionAnimation((view.context) as Activity, view, "moviePoster")
//            view.context.startActivity(intent, activityOptions.toBundle())
//        }
//
//        else{
//            view.context.startActivity(intent)
//        }


        (view.context as Activity).overridePendingTransition(R.anim.slide_in_right, R.anim.fix)
    }


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

    fun getRandomPageNumber(totalPage: Int?): Int{
        val rand = Random(System.nanoTime())

        return if(totalPage != 1) (1 .. totalPage!!).random(rand)
        else 1

    }

    fun getRandomPageIndex(totalNumber: Int, page : Int, totalPage: Int?): Int{
        val rand = Random(System.nanoTime())

        return if(totalNumber < 20) (0 until totalNumber).random(rand)
        else {

            if(page != totalPage) (0 until 20).random(rand)
            else{

                if(totalNumber % 20 == 0) (0 until 20).random(rand)
                else if(totalNumber % 20 == 1) 0
                else (0 until (totalNumber % 20)).random(rand)

            }

        }
    }

    fun getRandomYear(context: Context): String?{

        val rand = Random(System.nanoTime())

        when (MyApplication.answer3) {

            context.getString(R.string.dont_care) -> {
                return (1990..2021).random(rand).toString()
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

    fun getYearArray(context: Context):HashMap<Int, Int> {

        var yearHashMap = HashMap<Int, Int>()

        when(MyApplication.answer3.toString()){

            context.getString(R.string.dont_care) -> {
                yearHashMap[1990] = 1
                yearHashMap[1991] = 1
                yearHashMap[1992] = 1
                yearHashMap[1993] = 1
                yearHashMap[1994] = 1
                yearHashMap[1995] = 1
                yearHashMap[1996] = 1
                yearHashMap[1997] = 1
                yearHashMap[1998] = 1
                yearHashMap[1999] = 1
                yearHashMap[2000] = 1
                yearHashMap[2001] = 1
                yearHashMap[2002] = 1
                yearHashMap[2003] = 1
                yearHashMap[2004] = 1
                yearHashMap[2005] = 1
                yearHashMap[2006] = 1
                yearHashMap[2007] = 1
                yearHashMap[2008] = 1
                yearHashMap[2009] = 1
                yearHashMap[2010] = 1
                yearHashMap[2011] = 1
                yearHashMap[2012] = 1
                yearHashMap[2013] = 1
                yearHashMap[2014] = 1
                yearHashMap[2015] = 1
                yearHashMap[2016] = 1
                yearHashMap[2017] = 1
                yearHashMap[2018] = 1
                yearHashMap[2019] = 1
                yearHashMap[2020] = 1
                yearHashMap[2021] = 1
            }
            context.getString(R.string.date2020) -> {
                yearHashMap[2020] = 1
                yearHashMap[2021] = 1
            }
            context.getString(R.string.date2015) -> {
                yearHashMap[2015] = 1
                yearHashMap[2016] = 1
                yearHashMap[2017] = 1
                yearHashMap[2018] = 1
                yearHashMap[2019] = 1
            }
            context.getString(R.string.date2010) -> {
                yearHashMap[2010] = 1
                yearHashMap[2011] = 1
                yearHashMap[2012] = 1
                yearHashMap[2013] = 1
                yearHashMap[2014] = 1
            }
            context.getString(R.string.date2005) -> {
                yearHashMap[2005] = 1
                yearHashMap[2006] = 1
                yearHashMap[2007] = 1
                yearHashMap[2008] = 1
                yearHashMap[2009] = 1
            }
            context.getString(R.string.date2000) -> {
                yearHashMap[2000] = 1
                yearHashMap[2001] = 1
                yearHashMap[2002] = 1
                yearHashMap[2003] = 1
                yearHashMap[2004] = 1
            }
            context.getString(R.string.date2000before) ->{
                yearHashMap[1990] = 1
                yearHashMap[1991] = 1
                yearHashMap[1992] = 1
                yearHashMap[1993] = 1
                yearHashMap[1994] = 1
                yearHashMap[1995] = 1
                yearHashMap[1996] = 1
                yearHashMap[1997] = 1
                yearHashMap[1998] = 1
                yearHashMap[1999] = 1

            }

        }

        return yearHashMap
    }

    fun getTotalYearArray(context: Context): HashMap<Int, Int>{

        var yearHashMapTotal = HashMap<Int, Int>()

        when(MyApplication.answer3.toString()){

            context.getString(R.string.date2020) -> {
                yearHashMapTotal[2020] = 0
                yearHashMapTotal[2021] = 0
            }
            context.getString(R.string.date2015) -> {
                yearHashMapTotal[2015] = 0
                yearHashMapTotal[2016] = 0
                yearHashMapTotal[2017] = 0
                yearHashMapTotal[2018] = 0
                yearHashMapTotal[2019] = 0
            }
            context.getString(R.string.date2010) -> {
                yearHashMapTotal[2010] = 0
                yearHashMapTotal[2011] = 0
                yearHashMapTotal[2012] = 0
                yearHashMapTotal[2013] = 0
                yearHashMapTotal[2014] = 0
            }
            context.getString(R.string.date2005) -> {
                yearHashMapTotal[2005] = 0
                yearHashMapTotal[2006] = 0
                yearHashMapTotal[2007] = 0
                yearHashMapTotal[2008] = 0
                yearHashMapTotal[2009] = 0
            }
            context.getString(R.string.date2000) -> {
                yearHashMapTotal[2000] = 0
                yearHashMapTotal[2001] = 0
                yearHashMapTotal[2002] = 0
                yearHashMapTotal[2003] = 0
                yearHashMapTotal[2004] = 0
            }
            context.getString(R.string.date2000before) ->{
                yearHashMapTotal[1990] = 0
                yearHashMapTotal[1991] = 0
                yearHashMapTotal[1992] = 0
                yearHashMapTotal[1993] = 0
                yearHashMapTotal[1994] = 0
                yearHashMapTotal[1995] = 0
                yearHashMapTotal[1996] = 0
                yearHashMapTotal[1997] = 0
                yearHashMapTotal[1998] = 0
                yearHashMapTotal[1999] = 0

            }

        }
        return yearHashMapTotal
    }



}