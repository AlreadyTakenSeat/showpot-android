package com.alreadyoccupiedseat.model.comment

import androidx.annotation.Keep

@Keep
data class ReportOrBlockCommentRequest(
    val directInput: String,
    val reportType: String
)