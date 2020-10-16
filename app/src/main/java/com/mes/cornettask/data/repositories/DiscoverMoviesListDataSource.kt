package com.mes.cornettask.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mes.cornettask.data.api.MovieInterface
import com.mes.cornettask.data.pojos.MoviesResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * this class for getting list of movies from internet
 * */
class DiscoverMoviesListDataSource
    (
    private val apiService: MovieInterface,
    private val compositeDisposable: CompositeDisposable
) : BaseRepository() {

    private val _moviesListResponse = MutableLiveData<MoviesResponse>()
    val moviesListResponse: LiveData<MoviesResponse>
        get() = _moviesListResponse

    fun fetchMovieList() {
        networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                apiService.getDiscoverMovieAsync()
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _moviesListResponse.postValue(it)
                            networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
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