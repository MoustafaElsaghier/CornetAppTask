package com.mes.cornettask.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mes.cornettask.data.api.FIRST_PAGE
import com.mes.cornettask.data.api.MovieInterface
import com.mes.cornettask.data.pojos.MovieModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MoviesDataSource(
    private val apiService: MovieInterface,
    private val compositeDisposable: CompositeDisposable,
    private val searchKey: String,
) : PageKeyedDataSource<Int, MovieModel>() {

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieModel>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getSearchMovieAsync(searchKey, page)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.results, null, page + 1)
                    networkState.postValue(NetworkState.LOADED)
                }, {
                    networkState.postValue(NetworkState.ERROR)
                    it.message?.let { it1 -> Log.e("Search Movies Error", it1) }
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getSearchMovieAsync(searchKey, params.key)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.totalPages >= params.key) {
                        callback.onResult(it.results, params.key + 1)
                        networkState.postValue(NetworkState.LOADED)
                    } else {
                        networkState.postValue(NetworkState.END_OF_LIST)
                    }
                }, {
                    networkState.postValue(NetworkState.ERROR)
                    it.message?.let { it1 -> Log.e("Search Movies Error", it1) }
                })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {}
}