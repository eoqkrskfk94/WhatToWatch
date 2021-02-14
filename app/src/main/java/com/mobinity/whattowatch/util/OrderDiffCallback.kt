package com.mobinity.whattowatch.util

import androidx.recyclerview.widget.DiffUtil
import com.mobinity.whattowatch.model.MovieDb

class OrderDiffCallback(
    private var oldOrderList: ArrayList<MovieDb>,
    private var newOrderList: ArrayList<MovieDb>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldOrderList.size
    override fun getNewListSize(): Int = newOrderList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldOrderList[oldItemPosition] == newOrderList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame(oldItemPosition, newItemPosition)
    }
}