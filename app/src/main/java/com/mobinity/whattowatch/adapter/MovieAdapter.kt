package com.mobinity.whattowatch.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.mobinity.whattowatch.MyApplication
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.model.MovieDb
import com.mobinity.whattowatch.response.RemoteService
import com.mobinity.whattowatch.util.OrderDiffCallback
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieAdapter(val context: Context, private val movieList: ArrayList<MovieDb>, private val itemClick: (Int, String) -> Unit): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_poster, parent, false)
        return MovieViewHolder(itemView, itemClick)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        holder?.onBindViewHolder(movieList[position])
    }

    /**
     * 현재 아이템 리스트를 갱신한다.
     * @param orderInfoList 새로운 아이템 리스트
     */
    fun setData(newMovieDb: java.util.ArrayList<MovieDb>){
        val diffCallback = OrderDiffCallback(movieList, newMovieDb)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        movieList.clear()
        movieList.addAll(newMovieDb)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun getItemCount(): Int = movieList.size

    inner class MovieViewHolder(itemView: View, itemClick: (Int, String) -> Unit): RecyclerView.ViewHolder(itemView){

        private val ivPoster: ImageView = itemView.findViewById(R.id.iv_poster)
        private val loading: LottieAnimationView = itemView.findViewById(R.id.lav_loading)
        private val tvNoPoster: TextView = itemView.findViewById(R.id.tv_no_poster)
        private val movieDescription: TextView = itemView.findViewById(R.id.tv_movie_decription)

        fun onBindViewHolder(item: MovieDb){
            with(itemView){

                if(item.poster_path != null) Glide.with(context).load(RemoteService.MOVIE_POSTER_BASE_URL + item.poster_path).into(ivPoster)
                else {
                    tvNoPoster.visibility = View.VISIBLE
                    loading.visibility = View.GONE
                }
                ivPoster.scaleType = ImageView.ScaleType.CENTER_CROP
                //loading.visibility = View.GONE

                movieDescription.text = "${item.title}  (${item.release_date.substring(0, 4)})"

                getMovieProviders(item.id, itemView)

                itemView.setOnClickListener { itemClick(item.id, item.poster_path) }
            }
        }


    }




    private fun getMovieProviders(movieId: Int, itemView: View) {

        val retrofit = Retrofit.Builder()
                .baseUrl(RemoteService.MOVIE_DB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val handler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
            //Toast.makeText(context, "조회중 오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
        }

        MainScope().launch(handler) {

            val remoteService = retrofit.create(RemoteService::class.java)
            val response = remoteService.getMovieProviders(movieId, MyApplication.theMovieDataBaseKey)

            val ivNetflix: ImageView = itemView.findViewById(R.id.iv_netflix)
            val ivWatcha: ImageView = itemView.findViewById(R.id.iv_watcha)
            val ivWavve: ImageView = itemView.findViewById(R.id.iv_wavve)

            if (response.isSuccessful) {
                Log.d("TAG", "성공 : ${response.raw()}")


                if (response.body()?.results?.KR != null) {
                    for (item in response.body()?.results?.KR?.flatrate!!) {

                        println("$movieId, ${item.provider_name}")

                        when (item.provider_id) {
                            context.getString(R.string.netflix).toInt() -> ivNetflix.visibility = View.VISIBLE
                            context.getString(R.string.watcha).toInt() -> ivWatcha.visibility = View.VISIBLE
                            context.getString(R.string.wavve).toInt() -> ivWavve.visibility = View.VISIBLE
                        }
                    }
                }

            }
        }
    }



}