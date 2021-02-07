package com.mobinity.whattowatch

import android.app.Application

class MyApplication : Application() {

    companion object{

        val clientId = "FDv5XVzfPaLTQjQBxMW6"
        val clientSecret = "WuI4rAYOEh"

        val theMovieDataBaseKey = "13d2d1fab24ceba924f8e420696b56be"

        var answer1: String? = null
        var answer2: String? = null
        var answer3: String? = null
        var answer4: String? = null
        var answer5: String? = null

    }

    override fun onCreate() {
        super.onCreate()
    }

}