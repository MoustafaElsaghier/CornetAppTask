package com.mes.cornettask.data.api

import com.mes.cornettask.data.pojos.DiscoverMoviesResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MovieInterface {
    @GET("discover/movie")
    fun getDiscoverMovieAsync(@QueryMap hashMap: HashMap<String, String> = HashMap()): DiscoverMoviesResponse

    //    search/movie

}