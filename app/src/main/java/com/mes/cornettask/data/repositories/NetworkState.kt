package com.mes.cornettask.data.repositories

class NetworkState(val status: Status, val msg: String) {

    companion object {
        val LOADED: NetworkState = NetworkState(Status.SUCCESS, "Success")
        val LOADING: NetworkState = NetworkState(Status.RUNNING, "Running")
        val ERROR: NetworkState = NetworkState(Status.FAILED, "Something went wrong")
        val END_OF_LIST: NetworkState = NetworkState(Status.FAILED, "This is end of list")
    }
}

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}