package com.alreadyoccupiedseat.model

import androidx.annotation.Keep

@Keep
data class PagingData<T>(
    val size: Int,
    val hasNext: Boolean,
    val data: List<T>,
)
