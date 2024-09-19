package com.alreadyoccupiedseat.model

import androidx.annotation.Keep

@Keep
data class PagedResult<T>(
    val hasNext: Boolean,
    val data: List<T>,
)
