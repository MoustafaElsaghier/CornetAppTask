package com.mes.cornettask.ui.searchScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mes.cornettask.data.pojos.MoviesResponse
import com.mes.cornettask.data.repositories.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SearchMoviesListViewModel(
    private val moviesRepository: SearchMoviesListRepo,
    private val searchKey: String
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    /**
     * lazy to get it when needed not when initialized, better for performance.
     * */
    val searchMoviesList: LiveData<MoviesResponse> by lazy {
        moviesRepository.fetchSearchMovie(compositeDisposable, searchKey)
    }

    val networkState: LiveData<NetworkState> by lazy {
        moviesRepository.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}