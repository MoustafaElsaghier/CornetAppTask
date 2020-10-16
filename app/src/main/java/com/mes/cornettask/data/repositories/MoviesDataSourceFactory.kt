package com.mes.cornettask.data.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mes.cornettask.data.api.MovieInterface
import com.mes.cornettask.data.pojos.MovieModel
import io.reactivex.disposables.CompositeDisposable

class MoviesDataSourceFactory(
    private val apiService: MovieInterface,
    private val compositeDisposable: CompositeDisposable,
    private val searchKey: String
) : DataSource.Factory<Int, MovieModel>() {

    val moviesLiveDataSource = MutableLiveData<MoviesDataSource>()
    override fun create(): DataSource<Int, MovieModel> {
        val moviesDataSource = MoviesDataSource(apiService, compositeDisposable, searchKey)

        moviesLiveDataSource.postValue(moviesDataSource)
        return moviesDataSource
    }
}