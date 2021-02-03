package com.mobinity.whattowatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



//        val retrofit = Retrofit.Builder()
//            .baseUrl(RemoteService.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val api = retrofit.create(RemoteService::class.java)
//        val callGetSearchNews = api.searchMovieRetorfit(MyApplication.clientId, MyApplication.clientSecret, "avengers")

//        callGetSearchNews.enqueue(object : Callback<MovieItem> {
//            override fun onResponse(
//                call: Call<MovieItem>,
//                response: Response<MovieItem>
//            ) {
//                for(item in response.body()?.items!!){
//                    println(item.title)
//                }
//                Log.d("TAG", "성공 : ${response.raw()}")
//            }
//
//            override fun onFailure(call: Call<MovieItem>, t: Throwable) {
//                Log.d("TAG", "실패 : $t")
//            }
//        })




        val retrofit = Retrofit.Builder()
                .baseUrl(RemoteService.MOVIE_DB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create(RemoteService::class.java)
        val call = api.discoverMovie(MyApplication.theMovieDataBaseKey, "ko-KR", "28", "ko", 1)


        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
            ) {
                println(response.body()?.total_results)
                println(response.body()?.total_pages)
                Log.d("TAG", "성공 : ${response.raw()}")

                for(item in response.body()?.results!!){
                    println(item.title)
                    //println(item.overview)
                }

            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("TAG", "실패 : $t")
            }
        })


    }
}