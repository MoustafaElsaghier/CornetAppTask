package com.mes.cornettask.data.api

import com.mes.cornettask.data.pojos.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieInterface {

    @GET("discover/movie")
    fun getDiscoverMovieAsync(): Single<MoviesResponse>

    @GET("search/movie")
    fun getSearchMovieAsync(
        @Query("query") searchKey: String,
        @Query("page") page: Int
    ): Single<MoviesResponse>

}