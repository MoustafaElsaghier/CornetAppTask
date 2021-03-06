package com.mes.cornettask.ui.discoverScreen

import androidx.lifecycle.LiveData
import com.mes.cornettask.data.api.MovieInterface
import com.mes.cornettask.data.pojos.MoviesResponse
import com.mes.cornettask.data.repositories.DiscoverMoviesListDataSource
import com.mes.cornettask.data.repositories.NetworkState
import io.reactivex.disposables.CompositeDisposable

/**
 * repository class to manage data sources
 * */
class MoviesListRepo(private val apiService: MovieInterface) {
    lateinit var moviesListDataSource: DiscoverMoviesListDataSource

    fun fetchDiscoverMovie(compositeDisposable: CompositeDisposable): LiveData<MoviesResponse> {
        moviesListDataSource = DiscoverMoviesListDataSource(apiService, compositeDisposable)
        moviesListDataSource.fetchMovieList()

        return moviesListDataSource.moviesListResponse
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return moviesListDataSource.networkState
    }
}