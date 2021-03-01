package com.mobinity.whattowatch.view

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.mobinity.whattowatch.MyApplication
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.adapter.CastAdapter
import com.mobinity.whattowatch.adapter.ProviderAdapter
import com.mobinity.whattowatch.databinding.ActivityMovieDetailBinding
import com.mobinity.whattowatch.model.MovieProviderItem
import com.mobinity.whattowatch.response.MovieCreditResponse
import com.mobinity.whattowatch.response.RemoteService
import com.mobinity.whattowatch.viewModel.MovieDetailViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.util.regex.Pattern


class MovieDetailActivity : YouTubeBaseActivity() {

    private lateinit var movieDetailJob: Job
    private lateinit var handler: CoroutineExceptionHandler
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var providerAdapter: ProviderAdapter
    private lateinit var castAdapter: CastAdapter
    private lateinit var retrofit: Retrofit
    private lateinit var fadeIn: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMovieDetailBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_movie_detail
        )
        //binding.lifecycleOwner = this.l
        viewModel = MovieDetailViewModel()
        binding.viewModel = viewModel

        fadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)


        handler = viewModel.getCourotineHandler(this)
        retrofit = viewModel.getRetrofitService()

        setActionBarTransparent(binding)
        setRecyclerView(binding)


        val movieId = intent.getIntExtra("movieId", 0)
        val moviePoster = intent.getStringExtra("moviePoster")
        Glide.with(baseContext).load(RemoteService.MOVIE_POSTER_BASE_URL + moviePoster)
            .into(binding.ivMoviePoster)
        binding.ivMoviePoster.startAnimation(fadeIn)
        binding.ivMovieBackdrop.scaleType = ImageView.ScaleType.CENTER_CROP


        setMovieDetails(movieId, binding)

    }


    private fun setMovieDetails(movieId: Int, binding: ActivityMovieDetailBinding) {

        movieDetailJob = MainScope().launch {
            getMovieDetails(retrofit, movieId, binding)
            getMovieImages(retrofit, movieId, binding)
            getMovieCredits(retrofit, movieId, binding)
            getMovieProviders(retrofit, movieId, binding)
            setYoutubeTrailer(retrofit, movieId, binding)
            //getPersonDetail(retrofit, castIds, binding)
        }

    }

    private fun setPopularityProgressBar(binding: ActivityMovieDetailBinding, popularity: Int){

        binding.cpbPopularity.setProgressFormatter { progress, max ->
            val DEFAULT_PATTERN = "%d%%"
            String.format(DEFAULT_PATTERN, (progress.toFloat() / max.toFloat() * 100).toInt()) }

        val animator = ValueAnimator.ofInt(0, popularity)

        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int

            binding.cpbPopularity.progress = progress
        }

        animator.duration = 2000
        animator.start()

    }


    private fun setRecyclerView(binding: ActivityMovieDetailBinding) {
        providerAdapter = ProviderAdapter(
            this,
            ArrayList<MovieProviderItem.MovieProviderDetail>()
        ) { providerId ->

            println(providerId)

            when(providerId){

                getString(R.string.netflix).toInt() -> {

                    try {
                        val intent = Intent(Intent.ACTION_SEARCH)
                        intent.setClassName(
                            "com.netflix.mediaclient",
                            "com.netflix.mediaclient.ui.launch.UIWebViewActivity"
                        )
                        startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(this, "Please install the NetFlix App!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                getString(R.string.watcha).toInt() -> {

                    try {
                        val intent = Intent(Intent.ACTION_SEARCH)
                        intent.setClassName(
                            "com.frograms.wplay",
                            "com.frograms.wplay.activity.InitActivity"
                        )
                        startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(this, "Please install the Watcha App!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                getString(R.string.wavve).toInt() -> {

                    try {
                        val intent = Intent(Intent.ACTION_SEARCH)
                        intent.setClassName(
                            "kr.co.captv.pooqV2",
                            "kr.co.captv.pooqV2.main.IntroActivity"
                        )
                        startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(this, "Please install the wavve App!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }

        castAdapter = CastAdapter(
            this,
            ArrayList<MovieCreditResponse.Cast>()
        ){}


        binding.rvProviders.adapter = providerAdapter
        binding.rvProviders.layoutManager = LinearLayoutManager(this)

        binding.rvCasts.adapter = castAdapter
        binding.rvCasts.layoutManager = LinearLayoutManager(this)

        val horizontalLayout = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val horizontalLayout2 = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.rvProviders.layoutManager = horizontalLayout
        binding.rvCasts.layoutManager = horizontalLayout2

    }


    private suspend fun getMovieDetails(
        retrofit: Retrofit,
        movieId: Int,
        binding: ActivityMovieDetailBinding
    ) {

        val remoteService = retrofit.create(RemoteService::class.java)
        val response = remoteService.getMovieDetail(
            movieId,
            MyApplication.theMovieDataBaseKey,
            "ko-KR"
        )

        if (response.isSuccessful) {

            binding.tvMovieTitle2.text = response.body()?.title
            binding.tvMovieTitle.text = response.body()?.title


            var genres = viewModel.makeGenreString(this, response.body()?.genres!!)

            val sideTitle = "$genres · ${
                viewModel.makeMovieOriginString(
                    this,
                    response.body()?.production_countries!!
                )
            } · ${response.body()?.release_date?.substring(0, 4)}"

            binding.tvMovieSideTitle2.text = response.body()?.tagline
            binding.tvMovieSideTitle.text = sideTitle
            binding.tvMovieOverview.text = response.body()?.overview
            binding.tvMovieReleaseDate.text = response.body()?.release_date
            binding.tvMovieRuntime.text = "${response.body()?.runtime} 분"
            setPopularityProgressBar(binding, (response.body()?.vote_average?.times(10))?.toInt()!!)

        }


    }

    private suspend fun getMovieImages(
        retrofit: Retrofit,
        movieId: Int,
        binding: ActivityMovieDetailBinding
    ) {
        val remoteService = retrofit.create(RemoteService::class.java)
        val response = remoteService.getMovieImages(movieId, MyApplication.theMovieDataBaseKey)


        Log.d("TAG", "성공 : ${response.raw()}")

        if (response.isSuccessful) {

            binding.lavLoading.visibility = View.GONE

            if (response.body()?.backdrops?.size!! > 0) {

                Glide.with(baseContext)
                    .load(RemoteService.MOVIE_BACKDROP_BASE_URL + response.body()!!.backdrops[0]!!.file_path)
                    .into(
                        binding.ivMovieBackdrop
                    )
                binding.ivMovieBackdrop.scaleType = ImageView.ScaleType.CENTER_CROP

//                for(item in response.body()?.backdrops!!){
//                    println(item.file_path)
//                }

            } else {
                binding.tvNoBackdrop.visibility = View.VISIBLE
            }
        }

//        println(response.body()?.backdrops?.get(0)?.file_path)
    }

    private suspend fun getMovieProviders(
        retrofit: Retrofit,
        movieId: Int,
        binding: ActivityMovieDetailBinding
    ) {

        var providers = ArrayList<MovieProviderItem.MovieProviderDetail>()

        val remoteService = retrofit.create(RemoteService::class.java)
        val response = remoteService.getMovieProviders(movieId, MyApplication.theMovieDataBaseKey)

        if (response.isSuccessful) {
            Log.d("TAG", "성공 : ${response.raw()}")


            if (response.body()?.results?.KR != null) {

                if (response.body()?.results?.KR?.flatrate != null) {
                    providers = response.body()?.results?.KR?.flatrate!!
                    providerAdapter.setData(providers)
                    binding.rvProviders.adapter = providerAdapter
                }
            }

        }
    }

    private suspend fun getMovieCredits(
        retrofit: Retrofit,
        movieId: Int,
        binding: ActivityMovieDetailBinding
    ): ArrayList<MovieCreditResponse.Cast> {

        val remoteService = retrofit.create(RemoteService::class.java)
        val response = remoteService.getMovieCredits(
            movieId,
            MyApplication.theMovieDataBaseKey,
            "ko-KR"
        )

        Log.d("TAG", "성공 : ${response.raw()}")

        var castIds = ArrayList<MovieCreditResponse.Cast>()



        if(response.body()?.cast?.size!! > 10){

            for (cast in 0..10) {
                castIds.add(response.body()?.cast?.get(cast)!!)
            }

        }

        else{
            castIds = response.body()?.cast!!
        }

        for(cast in response.body()?.crew!!){

            if(cast.job == "Director"){
                println(cast.name)
                castIds.add(
                    0, MovieCreditResponse.Cast(
                        cast.id,
                        cast.name,
                        cast.profile_path,
                        cast.job
                    )
                )
            }

        }


        castAdapter.setData(castIds)
        binding.rvCasts.adapter = castAdapter



        return castIds

    }

    private suspend fun getPersonDetail(
        retrofit: Retrofit,
        castIds: ArrayList<Int>,
        binding: ActivityMovieDetailBinding
    ) {

        val p = Pattern.compile("[가-힣]")


        for (personId in castIds) {

            val remoteService = retrofit.create(RemoteService::class.java)
            val response = remoteService.getPerson(
                personId,
                MyApplication.theMovieDataBaseKey,
                "ko-KR"
            )

            for (item in response.body()?.also_known_as!!) {
                println(item)
            }

        }
    }

    private suspend fun setYoutubeTrailer(
        retrofit: Retrofit,
        movieId: Int,
        binding: ActivityMovieDetailBinding
    ) {

        val remoteService = retrofit.create(RemoteService::class.java)
        val response = remoteService.getMovieVideos(
            movieId,
            MyApplication.theMovieDataBaseKey,
            "ko-KR"
        )

        for (item in response.body()?.results!!) {
            println(item.key)
        }

        if(response.body()?.results!!.size > 0){


            binding.ytpvTrailer.initialize("AIzaSyDyzl82qS1UxbW38e6LIpyDLXVkaM9qcco",
                object : YouTubePlayer.OnInitializedListener {

                    override fun onInitializationSuccess(
                        provider: YouTubePlayer.Provider,
                        youTubePlayer: YouTubePlayer,
                        b: Boolean
                    ) {
                        println("ok")
                        youTubePlayer.cueVideo(response.body()?.results!!.get(0).key)
                    }

                    override fun onInitializationFailure(
                        provider: YouTubePlayer.Provider,
                        youTubeInitializationResult: YouTubeInitializationResult
                    ) {
                        println("fail")
                    }
                })

        }





    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun setActionBarTransparent(binding: ActivityMovieDetailBinding) {

        binding.actionBar.setBackgroundColor(getColor(R.color.main_background))
        binding.actionBar.background.alpha = 0
        binding.tvMovieTitle2.alpha = 0F

        binding.nsvMovieDetail.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            //println("$scrollX $scrollY")

            if (scrollY >= 0) {
                if (scrollY <= 500) {
                    binding.actionBar.setBackgroundColor(getColor(R.color.main_background))
                    binding.actionBar.background.alpha = (scrollY.toFloat() / 500.0 * 255.0).toInt()
                    binding.tvMovieTitle2.alpha = (scrollY.toFloat() / 500.0).toFloat()
                } else {
                    binding.actionBar.setBackgroundColor(getColor(R.color.main_background))
                    binding.actionBar.background.alpha = 255
                    binding.tvMovieTitle2.alpha = 1F
                }
            }
        }

    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fix, R.anim.slide_out_right)
    }


}