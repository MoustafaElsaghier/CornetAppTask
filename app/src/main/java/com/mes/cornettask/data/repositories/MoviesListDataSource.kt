package com.mes.cornettask.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mes.cornettask.data.api.MovieInterface
import com.mes.cornettask.data.pojos.DiscoverMoviesResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * this class for getting list of movies from internet
 * */
class MoviesListDataSource
    (
    private val apiService: MovieInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        //with this get, no need to implement get function to get networkSate
        get() = _networkState

    private val _moviesListResponse = MutableLiveData<DiscoverMoviesResponse>()
    val moviesListResponse: LiveData<DiscoverMoviesResponse>
        get() = _moviesListResponse

    fun fetchMovieList() {
        _networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                apiService.getDiscoverMovieAsync()
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _moviesListResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            it.message?.let { it1 -> Log.e("MovieDetailsDataSource", it1) }
                        }
                    )
            )

        } catch (e: Exception) {
            // ?.let {} that's make sure that block of code won't be execute it e.message is null
            e.message?.let { Log.e("MovieDetailsDataSource", it) }
        }


    }


}