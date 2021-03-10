package com.mobinity.whattowatch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.response.MovieCreditResponse
import com.mobinity.whattowatch.response.RemoteService
import org.w3c.dom.Text

class CastAdapter(val context: Context, private val castList: ArrayList<MovieCreditResponse.Cast>, private val itemClick: (Int) -> Unit): RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastAdapter.CastViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cast_item, parent, false)
        return CastViewHolder(itemView, itemClick)
    }

    override fun onBindViewHolder(holder: CastAdapter.CastViewHolder, position: Int) {
        holder?.onBindViewHolder(castList[position])
    }

    /**
     * 현재 아이템 리스트를 갱신한다.
     * @param orderInfoList 새로운 아이템 리스트
     */
    fun setData(newProviderList: java.util.ArrayList<MovieCreditResponse.Cast>){
        castList.clear()
        castList.addAll(newProviderList)
    }


    override fun getItemCount(): Int = castList.size

    inner class CastViewHolder(itemView: View, itemClick: (Int) -> Unit): RecyclerView.ViewHolder(itemView){


        var castImage: ImageView = itemView.findViewById(R.id.iv_cast)
        var castName: TextView = itemView.findViewById(R.id.tv_cast_name)
        var castCharacter: TextView = itemView.findViewById(R.id.tv_cast_character)
        var castJob: TextView = itemView.findViewById(R.id.tv_cast_job)
        var tvNoCastImage: TextView = itemView.findViewById(R.id.tv_no_cast_img)
        private val loading: LottieAnimationView = itemView.findViewById(R.id.lav_loading)


        fun onBindViewHolder(item: MovieCreditResponse.Cast){
            with(itemView){

                if(item.profile_path != null) Glide.with(context).load(RemoteService.MOVIE_CAST_BASE_URL + item.profile_path).into(castImage)
                else {
                    loading.visibility = View.GONE
                    tvNoCastImage.visibility = View.VISIBLE
                }
                castName.text = item.name
                castCharacter.text = item.character

                if(item.character == "Director") castJob.text = "감독"

            }
        }

    }


}