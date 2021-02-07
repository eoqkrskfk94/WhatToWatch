package com.mobinity.whattowatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.mobinity.whattowatch.response.RemoteService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        val retrofit = Retrofit.Builder()
//            .baseUrl(RemoteService.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val api = retrofit.create(RemoteService::class.java)
//        val callGetSearchNews = api.searchMovieRetorfit(MyApplication.clientId, MyApplication.clientSecret, "avengers")
//
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


        val handler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
            Toast.makeText(this, "조회중 오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
        }

        job = MainScope().launch(handler) {
            val retrofit = Retrofit.Builder()
                    .baseUrl(RemoteService.MOVIE_DB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            val remoteService = retrofit.create(RemoteService::class.java)
            val response = remoteService.discoverMovie(MyApplication.theMovieDataBaseKey, "ko-KR", "28", "ko", 1)

            Log.d("TAG", "성공 : ${response.raw()}")
            println(response.body()?.total_results)
            for (item in response.body()?.results!!) {
                println("영화이름: ${item.title}, id: ${item.id}")
            }
        }

        MainScope().launch(handler) {
            val retrofit = Retrofit.Builder()
                .baseUrl(RemoteService.MOVIE_DB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val remoteService = retrofit.create(RemoteService::class.java)
            val response = remoteService.getMovieProviders(518068, MyApplication.theMovieDataBaseKey)

            Log.d("TAG", "성공 : ${response.raw()}")

            println(response.body()?.results?.KR?.link)

            for (item in response.body()?.results?.KR?.flatrate!!) {
                println(item.provider_name)
                println(item.logo_path)
            }
        }
    }
}