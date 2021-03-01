package com.mobinity.whattowatch.response

class MovieDetailResponse {
    var id: Int = 0
    var title: String = ""
    var original_title: String = ""
    var overview: String? = ""
    var release_date: String = ""
    var poster_path: String = ""
    var tagline: String? = ""
    var homepage: String = ""
    var video: Boolean = false
    var runtime: Int? = 0
    var vote_average: Float = 0F
    lateinit var genres: ArrayList<Genre>
    lateinit var production_countries: ArrayList<ProductionCountries>

    data class Genre(
        var id: Int,
        var name: String
    )

    data class ProductionCountries(
            var name: String,
            var id: Int,
            var origin_country: String
    )
}