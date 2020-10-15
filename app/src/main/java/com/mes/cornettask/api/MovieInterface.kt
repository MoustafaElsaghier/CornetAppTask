package com.mes.cornettask.api

import com.mes.cornettask.models.DiscoverMoviesResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MovieInterface {
    @GET("discover/movie")
    fun getDiscoverMovieAsync(@QueryMap hashMap: HashMap<String, String> = HashMap()): DiscoverMoviesResponse

    //    search/movie

}