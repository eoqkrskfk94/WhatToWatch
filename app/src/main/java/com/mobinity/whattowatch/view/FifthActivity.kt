package com.mobinity.whattowatch.view

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mobinity.whattowatch.MyApplication
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.adapter.MovieAdapter
import com.mobinity.whattowatch.databinding.ActivityFifthBinding
import com.mobinity.whattowatch.model.MovieDb
import com.mobinity.whattowatch.response.RemoteService
import com.mobinity.whattowatch.util.BackPressCloseHandler
import com.mobinity.whattowatch.viewModel.FifthViewModel
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class FifthActivity : AppCompatActivity() {

    private lateinit var backPressCloseHandler: BackPressCloseHandler
    private lateinit var retrofitService: Retrofit
    private lateinit var handler: CoroutineExceptionHandler
    private lateinit var discoverMovieJob: Job
    private lateinit var viewModel: FifthViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var binding: ActivityFifthBinding
    private lateinit var fadeIn: Animation
    private lateinit var fadeIn2: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fifth)
        binding.lifecycleOwner = this
        viewModel = FifthViewModel()
        binding.viewModel = viewModel

        retrofitService = viewModel.getRetrofitService()
        handler = viewModel.getCourotineHandler(this)
        backPressCloseHandler = BackPressCloseHandler(this)

        setSelectedTypeText(binding)
        setTextAnimation(binding)
        setRecyclerView(binding)


        discoverMovie(retrofitService, handler)

        //searchAgainBtn()


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


//        MainScope().launch(handler) {
//            val retrofit = Retrofit.Builder()
//                    .baseUrl(RemoteService.MOVIE_DB_BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
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
        fadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)
        fadeIn2 = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)
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

//            binding.ivNetflix.visibility = View.GONE
//            binding.ivWatcha.visibility = View.GONE
//            binding.ivWavve.visibility = View.GONE
//            binding.tvMovieDecription.text = ""
//            binding.tvMovieClick.text = ""


            var result1 = getDiscoverMovieResultCount(retrofit)
//            val result2 = getDiscoverMovieResultCount(retrofit)
//            val result3 = getDiscoverMovieResultCount(retrofit)
//            val result4 = getDiscoverMovieResultCount(retrofit)
//            val result5 = getDiscoverMovieResultCount(retrofit)

            println(result1)


            if (result1[0] == 0) {
                while (result1[0] == 0) {
                    result1 = getDiscoverMovieResultCount(retrofit)
                }
            }


            val movieId = getDiscoverMovieResultList(result1, retrofit)


            //getMovieProviders(retrofit, movieId)

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
                        "KR",
                        MyApplication.answer4,
                        year,
                        null
                )

                Log.d("TAG", "성공 : ${response.raw()}")

                return@withContext listOf(
                        response.body()?.total_results,
                        response.body()?.total_pages,
                        year
                )

            }

    private suspend fun getDiscoverMovieResultList(resultCount: List<Int?>, retrofit: Retrofit) =
            withContext(currentCoroutineContext()) {

                val remoteService = retrofit.create(RemoteService::class.java)
                val randomPageNumber = viewModel.getRandomPageNumber(resultCount[1])
                val randomPageIndex = viewModel.getRandomPageIndex(resultCount[0]!!, randomPageNumber, resultCount[1])


                //println(randomPageIndex)


                val response = remoteService.discoverMovie(
                        MyApplication.theMovieDataBaseKey,
                        "ko-KR",
                        MyApplication.answer1,
                        MyApplication.answer2,
                        "KR",
                        MyApplication.answer4,
                        resultCount[2],
                        randomPageNumber
                )

                //Log.d("TAG", "성공 : ${response.raw()}")


                println("page: $randomPageNumber, index: $randomPageIndex, year: ${resultCount[2]}")


//                binding.lavLoading.visibility = View.GONE
//                Glide.with(baseContext).load(RemoteService.MOVIE_POSTER_BASE_URL + response.body()!!.results[randomPageIndex]!!.poster_path).into(binding.ivMoviePoster)
//                binding.ivMoviePoster.scaleType = ImageView.ScaleType.CENTER_CROP
//                binding.ivMoviePoster.startAnimation(fadeIn)
//
//
//                binding.movieId = response.body()!!.results[randomPageIndex]!!.id
//                binding.moviePoster = response.body()!!.results[randomPageIndex]!!.poster_path
//
//                //moviePosterClick(response.body()!!.results[randomPageIndex]!!.id)
//
//                binding.tvMovieDecription.text = "${response.body()!!.results[randomPageIndex]!!.title}  (${response.body()!!.results[randomPageIndex]!!.release_date.substring(0, 4)})"
//                binding.tvMovieClick.text = "영\n화\n\n상\n세\n정\n보\n\n포\n스\n터\n\n클\n릭"
//                fadeIn.duration = 1000
//                binding.tvMovieDecription.startAnimation(fadeIn)
//                binding.tvMovieClick.startAnimation(fadeIn)

                val sample = response.body()?.results
                movieAdapter.setData(sample!!)
                binding.rvMovieList.adapter = movieAdapter


//            for (item in response.body()?.results!!) {
//                println("영화이름: ${item.title}, id: ${item.id}, poster: ${item.poster_path}")
//            }

                return@withContext response.body()!!.results[randomPageIndex]!!.id

            }

//    private suspend fun getMovieProviders(retrofit: Retrofit, movieId: Int) {
//
//        val remoteService = retrofit.create(RemoteService::class.java)
//        val response = remoteService.getMovieProviders(movieId, MyApplication.theMovieDataBaseKey)
//
//        if (response.isSuccessful) {
//            Log.d("TAG", "성공 : ${response.raw()}")
//
//
//            if (response.body()?.results?.KR != null) {
//                for (item in response.body()?.results?.KR?.flatrate!!) {
////                    println(item.provider_name)
////                    println(item.provider_id)
//
//                    when (item.provider_id) {
//                        getString(R.string.netflix).toInt() -> binding.ivNetflix.visibility = View.VISIBLE
//                        getString(R.string.watcha).toInt() -> binding.ivWatcha.visibility = View.VISIBLE
//                        getString(R.string.wavve).toInt() -> binding.ivWavve.visibility = View.VISIBLE
//                    }
//                }
//            }
//
//        }
//    }

//    private fun moviePosterClick(movieId: Int) {
//
//
//        binding.ivMoviePoster.setOnClickListener {
//
//
//            var intent = Intent(this, MovieDetailActivity::class.java)
//            intent.putExtra("movieId", movieId)
//            //view.context.startActivity(intent)
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, binding.ivMoviePoster, "transitionImage")
//                startActivity(intent, activityOptions.toBundle())
//            } else {
//                startActivity(intent)
//            }
//
//        }
//
//
//    }


//    private fun searchAgainBtn() {
//
//        binding.btnSearchAgain.setOnClickListener {
//            binding.lavLoading.visibility = View.VISIBLE
//            binding.ivMoviePoster.setImageResource(0)
//            discoverMovie(retrofitService, handler)
//        }
//
//    }


    override fun onBackPressed() {
        backPressCloseHandler.onBackPressed()
    }
}