package com.mobinity.whattowatch.model

data class MovieProviderItem(
    val KR: MovieProviderRegion
){


    data class MovieProviderRegion(
        val link: String,
        val flatrate: ArrayList<MovieProviderDetail>,
        val rent: ArrayList<MovieProviderDetail>,
        val buy:  ArrayList<MovieProviderDetail>
    )


    data class MovieProviderDetail(
        val display_priority: Int,
        val logo_path: String,
        val provider_id: Int,
        val provider_name: String
    )
}

