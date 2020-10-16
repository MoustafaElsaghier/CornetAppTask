package com.mes.cornettask.ui.searchScreen

import androidx.lifecycle.LiveData
import com.mes.cornettask.data.api.MovieInterface
import com.mes.cornettask.data.pojos.MoviesResponse
import com.mes.cornettask.data.repositories.NetworkState
import com.mes.cornettask.data.repositories.SearchMoviesListDataSource
import io.reactivex.disposables.CompositeDisposable

/**
 * repository class to manage data sources
 * */
class SearchMoviesListRepo(private val apiService: MovieInterface) {
    lateinit var searchMoviesListDataSource: SearchMoviesListDataSource

    fun fetchSearchMovie(
        compositeDisposable: CompositeDisposable,
        searchKey: String
    ): LiveData<MoviesResponse> {
        searchMoviesListDataSource = SearchMoviesListDataSource(apiService, compositeDisposable)
        searchMoviesListDataSource.fetchMovieList(searchKey)

        return searchMoviesListDataSource.moviesListResponse
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return searchMoviesListDataSource.networkState
    }
}