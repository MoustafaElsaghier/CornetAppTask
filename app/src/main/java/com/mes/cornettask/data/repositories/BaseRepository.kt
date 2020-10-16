package com.mes.cornettask.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class BaseRepository {
    val networkState = MutableLiveData<NetworkState>()
    val network: LiveData<NetworkState>
        //with this get, no need to implement get function to get networkSate
        get() = networkState

}