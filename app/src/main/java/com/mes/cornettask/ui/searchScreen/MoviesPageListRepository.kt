package com.mes.cornettask.ui.searchScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mes.cornettask.data.api.ITEMS_PER_PAGE
import com.mes.cornettask.data.api.MovieInterface
import com.mes.cornettask.data.pojos.MovieModel
import com.mes.cornettask.data.repositories.MoviesDataSource
import com.mes.cornettask.data.repositories.MoviesDataSourceFactory
import com.mes.cornettask.data.repositories.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviesPageListRepository(private val apiService: MovieInterface) {
    lateinit var moviesPagedList: LiveData<PagedList<MovieModel>>
    lateinit var moviesDataSourceFactory: MoviesDataSourceFactory

    fun fetchLiveMoviePagedList(
        compositeDisposable: CompositeDisposable,
        searchKey: String
    ): LiveData<PagedList<MovieModel>> {
        moviesDataSourceFactory =
            MoviesDataSourceFactory(apiService, compositeDisposable, searchKey)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(ITEMS_PER_PAGE)
            .build()

        moviesPagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviesPagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap(
            moviesDataSourceFactory.moviesLiveDataSource, MoviesDataSource::networkState
        )
    }
}