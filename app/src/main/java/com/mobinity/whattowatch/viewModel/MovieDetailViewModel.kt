package com.mobinity.whattowatch.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.response.MovieDetailResponse
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

    fun makeGenreString(context: Context, genres: ArrayList<MovieDetailResponse.Genre>): String{

        var genreString = ""

        for(genre in genres){

            when(genre.id){

                context.getString(R.string.action).toInt() -> genreString += "${context.getString(R.string.action_kr)}/"
                context.getString(R.string.sf).toInt() -> genreString += "${context.getString(R.string.sf_kr)}/"
                context.getString(R.string.drama).toInt() -> genreString += "${context.getString(R.string.drama_kr)}/"
                context.getString(R.string.comedy).toInt() -> genreString += "${context.getString(R.string.comedy_kr)}/"
                context.getString(R.string.thriller).toInt() -> genreString += "${context.getString(R.string.thriller_kr)}/"
                context.getString(R.string.animation).toInt() -> genreString += "${context.getString(R.string.animation_kr)}/"
                context.getString(R.string.horror).toInt() -> genreString += "${context.getString(R.string.horror_kr)}/"
                context.getString(R.string.fantasy).toInt() -> genreString += "${context.getString(R.string.fantasy_kr)}/"
                context.getString(R.string.romance).toInt() -> genreString += "${context.getString(R.string.romance_kr)}/"
                context.getString(R.string.mystery).toInt() -> genreString += "${context.getString(R.string.mystery_kr)}/"
                context.getString(R.string.family).toInt() -> genreString += "${context.getString(R.string.family_kr)}/"
                context.getString(R.string.music).toInt() -> genreString += "${context.getString(R.string.music_kr)}/"
                context.getString(R.string.documentary).toInt() -> genreString += "${context.getString(R.string.documentary_kr)}/"
            }

        }

        genreString = genreString.dropLast(1)

        return genreString
    }

    fun makeMovieOriginString(context: Context, productionCountries: ArrayList<MovieDetailResponse.ProductionCountries>): String{

        var originString = "null"


        if(productionCountries.size > 0){


            when(productionCountries[0].name){



                context.getString(R.string.USA)-> originString = "미국"
                context.getString(R.string.Korea) -> originString = "대한한국"
                context.getString(R.string.Japan) -> originString = "일본"
                context.getString(R.string.France)-> originString = "대만"
                context.getString(R.string.China) -> originString = "중국"
                context.getString(R.string.HongKong) -> originString = "홍콩"
                context.getString(R.string.UK) -> originString = "영국"

            }

        }


        return originString
    }
}