package com.mes.cornettask.ui.searchScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mes.cornettask.data.pojos.MovieModel
import com.mes.cornettask.data.repositories.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SearchFragmentViewModel(
    private val moviesRepository: MoviesPageListRepository,
    private val searchKey: String
) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val moviesPagedList: LiveData<PagedList<MovieModel>> by lazy {
        moviesRepository.fetchLiveMoviePagedList(compositeDisposable, searchKey)
    }

    val networkState: LiveData<NetworkState> by lazy {
        moviesRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviesPagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}