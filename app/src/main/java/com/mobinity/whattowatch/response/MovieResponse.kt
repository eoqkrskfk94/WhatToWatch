package com.mobinity.whattowatch.response

import com.mobinity.whattowatch.model.MovieDb

class MovieResponse {
    var page: Int = 0
    var results: ArrayList<MovieDb> = ArrayList()
    var total_results: Int = 0
    var total_pages: Int = 0
}