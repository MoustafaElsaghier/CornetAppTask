package com.mes.cornettask.ui.searchScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mes.cornettask.data.pojos.MovieModel
import com.mes.cornettask.data.repositories.NetworkState
import io.reactivex.disposables.CompositeDisposable


class SearchFragmentViewModel(
    private val moviesRepository: MoviesPageListRepository
) : ViewModel() {
    var searchText = MutableLiveData<String>()
        set(value) {
            field = value
            searchText.postValue(value.value)
//            moviesPagedList =
//                searchText.value?.let {
//                    moviesRepository.fetchLiveMoviePagedList(compositeDisposable,
//                        it
//                    )
//                }!!
        }


    private val compositeDisposable = CompositeDisposable()

    val moviesPagedList: LiveData<PagedList<MovieModel>> by lazy {
        moviesRepository.fetchLiveMoviePagedList(
            compositeDisposable, searchText.value.toString()
        )
    }

    val moviesPagedList2: LiveData<PagedList<MovieModel>> by lazy {
        moviesRepository.fetchLiveMoviePagedList(
            compositeDisposable, searchText.value.toString()
        )
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