package com.mes.cornettask.ui.discoverScreen

import androidx.lifecycle.LiveData
import com.mes.cornettask.data.api.MovieInterface
import com.mes.cornettask.data.pojos.DiscoverMoviesResponse
import com.mes.cornettask.data.repositories.MoviesListDataSource
import com.mes.cornettask.data.repositories.NetworkState
import io.reactivex.disposables.CompositeDisposable

/**
 * repository class to manage data sources
 * */
class MoviesListRepo(private val apiService: MovieInterface) {
    lateinit var moviesListDataSource: MoviesListDataSource

    fun fetchDiscoverMovie(compositeDisposable: CompositeDisposable): LiveData<DiscoverMoviesResponse> {
        moviesListDataSource = MoviesListDataSource(apiService, compositeDisposable)
        moviesListDataSource.fetchMovieList()

        return moviesListDataSource.moviesListResponse
    }

    fun getMoviesNetworkState(): LiveData<NetworkState> {
        return moviesListDataSource.networkState
    }
}