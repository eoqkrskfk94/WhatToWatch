package com.mobinity.whattowatch.response

import com.mobinity.whattowatch.model.MovieItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteService {

    companion object{
        var BASE_URL = "https://openapi.naver.com/"
        var MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/"

    }

    @GET("v1/search/movie.json")
    suspend fun searchMovie(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") query: String,
        @Query("display") display: Int? = null,
        @Query("start") start: Int? = null,
        @Query("genre") genre: String? = null,
        @Query("country") country: Int? = null,
        @Query("yearfrom") yearFrom: Int? = null,
        @Query("yearto") yearTo: Int? = null
    ): MovieItem

    @GET("v1/search/movie.json")
    fun searchMovieRetorfit(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") query: String,
        @Query("display") display: Int? = null,
        @Query("start") start: Int? = null,
        @Query("genre") genre: String? = null,
        @Query("country") country: String? = null,
        @Query("yearfrom") yearFrom: Int? = null,
        @Query("yearto") yearTo: Int? = null
    ):Call<MovieItem>


    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
            @Path("movie_id") movieId: Int,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ):Response<MovieDetailResponse>


    @GET("discover/movie")
    suspend fun discoverMovie(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("with_genres") genre: String?,
            @Query("with_original_language") region: String?,
            @Query("with_watch_providers") providers: String?,
            @Query("year") year: Int?,
            @Query("page") page: Int?
    ):Response<DiscoverMovieResponse>


    @GET("movie/{movie_id}/watch/providers")
    suspend fun getMovieProviders(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ):Response<MovieProviderResponse>









}