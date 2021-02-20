package com.mobinity.whattowatch.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.mobinity.whattowatch.MyApplication
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.databinding.ActivityMovieDetailBinding
import com.mobinity.whattowatch.response.RemoteService
import com.mobinity.whattowatch.viewModel.MovieDetailViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var movieDetailJob: Job
    private lateinit var handler: CoroutineExceptionHandler
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        binding.lifecycleOwner = this
        viewModel = MovieDetailViewModel()
        binding.viewModel = viewModel


        handler = viewModel.getCourotineHandler(this)
        retrofit = viewModel.getRetrofitService()

        setActionBarTransparent(binding)


        val movieId = intent.getIntExtra("movieId", 0)
        val moviePoster = intent.getStringExtra("moviePoster")
        Glide.with(baseContext).load(RemoteService.MOVIE_POSTER_BASE_URL + moviePoster).into(binding.ivMoviePoster)


        println(movieId)

        setMovieDetails(movieId, binding)
    }


    private fun setMovieDetails(movieId: Int, binding: ActivityMovieDetailBinding){

        movieDetailJob = MainScope().launch {

            getMovieDetails(retrofit, movieId, binding)
            getMovieImages(retrofit, movieId, binding)

        }

    }

    private suspend fun getMovieDetails(retrofit: Retrofit, movieId: Int, binding: ActivityMovieDetailBinding){

        val remoteService = retrofit.create(RemoteService::class.java)
        val response = remoteService.getMovieDetail(movieId, MyApplication.theMovieDataBaseKey, "ko-KR")


    }

    private suspend fun getMovieImages(retrofit: Retrofit, movieId: Int, binding: ActivityMovieDetailBinding){
        val remoteService = retrofit.create(RemoteService::class.java)
        val response = remoteService.getMovieImages(movieId, MyApplication.theMovieDataBaseKey)


        Log.d("TAG", "성공 : ${response.raw()}")

        if(response.isSuccessful){

            binding.lavLoading.visibility = View.GONE

            if(response.body()?.backdrops?.size!! > 0){

                Glide.with(baseContext).load(RemoteService.MOVIE_BACKDROP_BASE_URL + response.body()!!.backdrops[0]!!.file_path).into(binding.ivMovieBackdrop)
                binding.ivMovieBackdrop.scaleType = ImageView.ScaleType.CENTER_CROP

//                for(item in response.body()?.backdrops!!){
//                    println(item.file_path)
//                }

            }

        }

//        println(response.body()?.backdrops?.get(0)?.file_path)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setActionBarTransparent(binding: ActivityMovieDetailBinding){


        binding.actionBar.setBackgroundColor(getColor(R.color.main_background))
        binding.actionBar.background.alpha = 0

        binding.nsvMovieDetail.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            //println("$scrollX $scrollY")

            if(scrollY >= 0){
                if(scrollY <= 500){
                    binding.actionBar.setBackgroundColor(getColor(R.color.main_background))
                    binding.actionBar.background.alpha = (scrollY.toFloat()/500.0 * 255.0).toInt()
                }
                else{
                    binding.actionBar.setBackgroundColor(getColor(R.color.main_background))
                    binding.actionBar.background.alpha = 255
                }
            }
        }

    }



    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fix, R.anim.slide_out_right)
    }


}