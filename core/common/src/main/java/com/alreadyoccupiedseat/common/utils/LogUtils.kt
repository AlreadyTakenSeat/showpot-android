package com.alreadyoccupiedseat.common.utils

import android.util.Log
import androidx.lifecycle.ViewModel

fun ViewModel.errorLog(content: Any) {
    Log.e(this.toString().split(".").last(), content.toString())
}