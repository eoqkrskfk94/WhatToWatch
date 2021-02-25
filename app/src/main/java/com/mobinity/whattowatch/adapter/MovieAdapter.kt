package com.mobinity.whattowatch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.model.MovieDb
import com.mobinity.whattowatch.response.RemoteService
import com.mobinity.whattowatch.util.OrderDiffCallback

class MovieAdapter(val context: Context, private val movieList: ArrayList<MovieDb>, private val itemClick: (Int) -> Unit): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

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

    inner class MovieViewHolder(itemView: View, itemClick: (Int) -> Unit): RecyclerView.ViewHolder(itemView){

        private val ivPoster: ImageView = itemView.findViewById(R.id.iv_poster)
        private val loading: LottieAnimationView = itemView.findViewById(R.id.lav_loading)
        private val movieDescription: TextView = itemView.findViewById(R.id.tv_movie_decription)

        fun onBindViewHolder(item: MovieDb){
            with(itemView){
                Glide.with(context).load(RemoteService.MOVIE_POSTER_BASE_URL + item.poster_path).into(ivPoster)
                ivPoster.scaleType = ImageView.ScaleType.CENTER_CROP
                //loading.visibility = View.GONE

                movieDescription.text = "${item.title}  (${item.release_date.substring(0, 4)})"
            }
        }

    }


}