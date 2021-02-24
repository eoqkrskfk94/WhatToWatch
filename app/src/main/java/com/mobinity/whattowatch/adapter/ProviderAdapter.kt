package com.mobinity.whattowatch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobinity.whattowatch.R
import com.mobinity.whattowatch.model.MovieDb
import com.mobinity.whattowatch.model.MovieProviderItem
import com.mobinity.whattowatch.response.RemoteService
import com.mobinity.whattowatch.util.OrderDiffCallback

class ProviderAdapter(val context: Context, private val providerList: ArrayList<MovieProviderItem.MovieProviderDetail>, private val itemClick: (Int) -> Unit): RecyclerView.Adapter<ProviderAdapter.ProviderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderAdapter.ProviderViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_provider, parent, false)
        return ProviderViewHolder(itemView, itemClick)
    }

    override fun onBindViewHolder(holder: ProviderAdapter.ProviderViewHolder, position: Int) {
        holder?.onBindViewHolder(providerList[position])
    }

    /**
     * 현재 아이템 리스트를 갱신한다.
     * @param orderInfoList 새로운 아이템 리스트
     */
    fun setData(newProviderList: java.util.ArrayList<MovieProviderItem.MovieProviderDetail>){
        providerList.clear()
        providerList.addAll(newProviderList)
    }


    override fun getItemCount(): Int = providerList.size

    inner class ProviderViewHolder(itemView: View, itemClick: (Int) -> Unit): RecyclerView.ViewHolder(itemView){

        private val providerCard: CardView = itemView.findViewById(R.id.cv_provider)
        private val ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
        private val tvDescription: TextView = itemView.findViewById(R.id.tv_icon_description)

        fun onBindViewHolder(item: MovieProviderItem.MovieProviderDetail){
            with(itemView){
                when(item.provider_id){
                    context.getString(R.string.netflix).toInt() -> {
                        ivIcon.setImageResource(R.drawable.netflix_icon_no_border)
                        tvDescription.text = "넷플릭스"
                    }
                    context.getString(R.string.watcha).toInt() -> {
                        ivIcon.setImageResource(R.drawable.watcha_icon_no_border)
                        tvDescription.text = "왓챠"
                    }
                    context.getString(R.string.wavve).toInt() -> {
                        providerCard.setCardBackgroundColor(context.getColor(R.color.white))
                        ivIcon.setImageResource(R.drawable.wavve_icon_no_border)
                        tvDescription.text = "웨이브"
                        tvDescription.setTextColor(context.getColor(R.color.black))
                    }
                }
            }
        }

    }


}