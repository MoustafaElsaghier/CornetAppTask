package com.mes.cornettask.ui.discoverScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mes.cornettask.data.pojos.DiscoverMoviesResponse
import com.mes.cornettask.data.repositories.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviesListViewModel(private val moviesRepository: MoviesListRepo) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    /**
     * lazy to get it when needed not when initalized, better for performance.
     * */
    val moviesList: LiveData<DiscoverMoviesResponse> by lazy {
        moviesRepository.fetchDiscoverMovie(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        moviesRepository.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}