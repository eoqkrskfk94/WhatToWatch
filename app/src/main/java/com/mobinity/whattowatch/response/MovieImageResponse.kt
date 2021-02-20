package com.mobinity.whattowatch.response

class MovieImageResponse {
    var id: Int = 0
    lateinit var backdrops: ArrayList<Backdrops>


    data class Backdrops(
            var aspect_ratio: Float,
            var file_path: String,
            var height: Int,
            var width: Int
    )
}