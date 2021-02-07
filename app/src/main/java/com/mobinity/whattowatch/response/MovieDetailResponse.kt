package com.mobinity.whattowatch.response

class MovieDetailResponse {
    var id: Int = 0
    var title: String = ""
    var original_title: String = ""
    var overview: String = ""
    var release_date: String = ""
    var poster_path: String = ""
    var tagline: String = ""
    var homepage: String = ""
    lateinit var genres: ArrayList<Genre>

    data class Genre(
        var id: Int,
        var name: String
    )
}