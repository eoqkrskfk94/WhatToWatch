package com.mobinity.whattowatch.model

data class MovieItem(
    var lastBuildDate: String = "",
    var total: Int = 0,
    var start: Int = 0,
    var display: Int = 0,
    var items: ArrayList<Items>

)

data class Items(
    var title: String = "",
    var link: String = "",
    var image: String = "",
    var subtitle: String = "",
    var pubDate: String = "",
    var director: String = "",
    var actor: String = "",
    var userRating: String = ""
)
