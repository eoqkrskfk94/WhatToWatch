package com.mobinity.whattowatch.model

data class MovieDb(
    var id: Int = 0,
    var original_title: String = "",
    var title: String = "",
    var poster_path: String = "",
    var release_date: String = "",
    var overview: String = "",
    var original_language: String = "",
    var runtime: Int = 0
)
