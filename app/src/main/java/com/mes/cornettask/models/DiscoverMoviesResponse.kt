package com.mes.cornettask.models


import com.google.gson.annotations.SerializedName

data class DiscoverMoviesResponse(
    val page: Int,
    val results: List<MovieModel>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)