package com.mes.cornettask.data.api

import com.mes.cornettask.data.pojos.DiscoverMoviesResponse
import io.reactivex.Single
import retrofit2.http.GET

interface MovieInterface {
    @GET("discover/movie")
    fun getDiscoverMovieAsync(): Single<DiscoverMoviesResponse>

    //    search/movie

}