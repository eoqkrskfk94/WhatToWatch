package com.mobinity.whattowatch

import android.app.Application

class MyApplication : Application() {

    companion object{

        val clientId = "FDv5XVzfPaLTQjQBxMW6"
        val clientSecret = "WuI4rAYOEh"

        val theMovieDataBaseKey = "13d2d1fab24ceba924f8e420696b56be"

    }

    override fun onCreate() {
        super.onCreate()
    }

}