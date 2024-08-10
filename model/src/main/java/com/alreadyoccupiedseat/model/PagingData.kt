package com.alreadyoccupiedseat.model

data class PagingData<T>(
    val size: Int,
    val hasNext: Boolean,
    val data: List<T>,
)
