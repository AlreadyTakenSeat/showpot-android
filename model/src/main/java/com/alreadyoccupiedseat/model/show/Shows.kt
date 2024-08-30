package com.alreadyoccupiedseat.model.show

data class Shows(
    val data: List<Data>,
    val hasNext: Boolean,
    val size: Int
) {
    companion object {
        const val RECENT = "RECENT"
        const val POPULAR = "POPULAR"
    }
}