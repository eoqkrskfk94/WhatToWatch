package com.mobinity.whattowatch.response

class MovieVideoResponse {
    var id: Int = 0
    lateinit var results: ArrayList<YoutubeVideo>


    data class YoutubeVideo(
            var id: String,
            var site: String,
            var key: String
    )
}