package com.mobinity.whattowatch.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobinity.whattowatch.MyApplication
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.adapter.MovieAdapter
import com.mobinity.whattowatch.databinding.ActivityFifthBinding
import com.mobinity.whattowatch.model.MovieDb
import com.mobinity.whattowatch.response.RemoteService
import com.mobinity.whattowatch.viewModel.FifthViewModel
import kotlinx.coroutines.*
import retrofit2.Retrofit

class FifthActivity : AppCompatActivity() {

    private lateinit var retrofitService: Retrofit
    private lateinit var handler: CoroutineExceptionHandler
    private lateinit var discoverMovieJob: Job
    private lateinit var viewModel: FifthViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var binding: ActivityFifthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fifth)
        binding.lifecycleOwner = this
        viewModel = FifthViewModel()
        binding.viewModel = viewModel

        retrofitService = viewModel.getRetrofitService()
        handler = viewModel.getCourotineHandler(this)

        setSelectedTypeText(binding)
        setTextAnimation(binding)
        setRecyclerView(binding)


        discoverMovie(retrofitService, handler)


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


//
//        MainScope().launch(handler) {
//            val retrofit = Retrofit.Builder()
//                .baseUrl(RemoteService.MOVIE_DB_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//            val remoteService = retrofit.create(RemoteService::class.java)
//            val response = remoteService.getMovieProviders(518068, MyApplication.theMovieDataBaseKey)
//
//            Log.d("TAG", "성공 : ${response.raw()}")
//
//            println(response.body()?.results?.KR?.link)
//
//            for (item in response.body()?.results?.KR?.flatrate!!) {
//                println(item.provider_name)
//                println(item.provider_id)
//            }
//        }

//        MainScope().launch(handler) {
//            val retrofit = Retrofit.Builder()
//                .baseUrl(RemoteService.MOVIE_DB_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//            val remoteService = retrofit.create(RemoteService::class.java)
//            val response = remoteService.getMovieDetail(518068, MyApplication.theMovieDataBaseKey, "ko-KR")
//
//            Log.d("TAG", "성공 : ${response.raw()}")
//
//            println(response.body()?.original_title)
//
//            for(genre in response.body()?.genres!!) println("${genre.id}  ${genre.name}")
//
//        }


    }


    private fun setSelectedTypeText(binding: ActivityFifthBinding) {

        var answer1 = ""
        var answer2 = ""
        var answer3 = ""
        var answer4 = ""

        println(MyApplication.answer1)
        println()

        when (MyApplication.answer1.toString()) {

            getString(R.string.action) -> answer1 = "액션 · "
            getString(R.string.drama) -> answer1 = "드라마 · "
            getString(R.string.fantasy) -> answer1 = "판타지 · "
            getString(R.string.sf) -> answer1 = "SF · "
            getString(R.string.documentary) -> answer1 = "다큐먼터리 · "
            getString(R.string.horror) -> answer1 = "공포 · "
            getString(R.string.thriller) -> answer1 = "스릴러 · "
            getString(R.string.mystery) -> answer1 = "미스터리 · "
            getString(R.string.comedy) -> answer1 = "코미디 · "
            getString(R.string.music) -> answer1 = "뮤지컬 · "
            getString(R.string.family) -> answer1 = "가족 · "
            getString(R.string.animation) -> answer1 = "애니메이션 · "
            getString(R.string.romance) -> answer1 = "로멘스 · "

        }

        when (MyApplication.answer2) {

            getString(R.string.korea) -> answer2 = "한국 · "
            getString(R.string.english) -> answer2 = "미국/영국 · "
            getString(R.string.japan) -> answer2 = "일본 · "
            getString(R.string.france) -> answer2 = "프랑스 · "
            getString(R.string.china) -> answer2 = "중국 · "

        }

        when (MyApplication.answer3) {

            getString(R.string.date2020) -> answer3 = "2020 ~ 현재 · "
            getString(R.string.date2015) -> answer3 = "2015 ~ 2019 · "
            getString(R.string.date2010) -> answer3 = "2010 ~ 2014 · "
            getString(R.string.date2005) -> answer3 = "2005 ~ 2009 · "
            getString(R.string.date2000) -> answer3 = "2000 ~ 2004 · "
            getString(R.string.date2000before) -> answer3 = "~ 1999 · "

        }

        when (MyApplication.answer4) {

            getString(R.string.netflix) -> answer4 = "넷플릭스"
            getString(R.string.watcha) -> answer4 = "왓챠"
            getString(R.string.wavve) -> answer4 = "웨이브"

        }



        binding.tvSelectedTypes.text = "$answer1$answer2$answer3$answer4"


    }


    private fun setTextAnimation(binding: ActivityFifthBinding) {
        val fadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)
        val fadeIn2 = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)
        fadeIn.duration = 2000
        fadeIn2.duration = 2000

        binding.tvCurrentQuestion.startAnimation(fadeIn)
        binding.tvQuestion5.startAnimation(fadeIn)
        binding.tvSelectedTypes.startAnimation(fadeIn)


    }

    private fun setRecyclerView(binding: ActivityFifthBinding) {
        movieAdapter = MovieAdapter(
            this,
            ArrayList<MovieDb>()
        ) {


        }

        binding.rvMovieList.adapter = movieAdapter
        binding.rvMovieList.layoutManager = LinearLayoutManager(this)

        val horizontalLayout = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.rvMovieList.layoutManager = horizontalLayout

    }

    private fun discoverMovie(retrofit: Retrofit, handler: CoroutineExceptionHandler) {

        discoverMovieJob = MainScope().launch(handler) {


            val result1 = getDiscoverMovieResultCount(retrofit)
//            val result2 = getDiscoverMovieResultCount(retrofit)
//            val result3 = getDiscoverMovieResultCount(retrofit)
//            val result4 = getDiscoverMovieResultCount(retrofit)
//            val result5 = getDiscoverMovieResultCount(retrofit)

            println(result1)



            getDiscoverMovieResultList(result1, retrofit)


        }


    }

    private suspend fun getDiscoverMovieResultCount(retrofit: Retrofit) =
        withContext(currentCoroutineContext()) {

            val remoteService = retrofit.create(RemoteService::class.java)


            val year = viewModel.getRandomYear(baseContext)?.toInt()

            val response = remoteService.discoverMovie(
                MyApplication.theMovieDataBaseKey,
                "ko-KR",
                MyApplication.answer1,
                MyApplication.answer2,
                MyApplication.answer4,
                year,
                null
            )

//        Log.d("TAG", "성공 : ${response.raw()} $year")
//

            return@withContext listOf(
                response.body()?.total_results,
                response.body()?.total_pages,
                year
            )

        }

    private suspend fun getDiscoverMovieResultList(resultCount: List<Int?>, retrofit: Retrofit) =
        withContext(currentCoroutineContext()) {

            val remoteService = retrofit.create(RemoteService::class.java)

            val response = remoteService.discoverMovie(
                MyApplication.theMovieDataBaseKey,
                "ko-KR",
                MyApplication.answer1,
                MyApplication.answer2,
                MyApplication.answer4,
                resultCount[2],
                null
            )

            Log.d("TAG", "성공 : ${response.raw()}")

            val sample = response.body()?.results

            movieAdapter.setData(sample!!)
            binding.rvMovieList.adapter = movieAdapter



            for (item in response.body()?.results!!) {
                println("영화이름: ${item.title}, id: ${item.id}, poster: ${item.poster_path}")
            }

        }


    override fun finish() {
        val intent = Intent(this, FirstActivity::class.java)
        startActivity(intent)
        super.finish()
        overridePendingTransition(R.anim.fix, R.anim.slide_out_right)
    }
}