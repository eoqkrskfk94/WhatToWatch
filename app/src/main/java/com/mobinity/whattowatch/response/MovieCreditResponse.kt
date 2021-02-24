package com.mobinity.whattowatch.response

class MovieCreditResponse(
        var id: Int,
        var cast: ArrayList<Cast>,
        var crew: ArrayList<Crew>
){
    data class Cast(
            var id: Int,
            var name: String,
            var profile_path: String,
            var character: String
    )

    data class Crew(
            var id: Int,
            var name: String,
            var profile_path: String,
            var job: String
    )
}