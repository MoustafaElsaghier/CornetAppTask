package com.mes.cornettask.ui.discoverScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mes.cornettask.data.pojos.MoviesResponse
import com.mes.cornettask.data.repositories.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviesListViewModel(private val moviesRepository: MoviesListRepo) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    /**
     * lazy to get it when needed not when initialized, better for performance.
     * */
    val moviesList: LiveData<MoviesResponse> by lazy {
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