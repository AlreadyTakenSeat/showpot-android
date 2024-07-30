package com.alreadyoccupiedseat.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.searchHistoryDataStore: DataStore<Preferences> by preferencesDataStore(name = "searchHistory")

class SearchHistoryDataStore @Inject constructor(@ApplicationContext private val context: Context) {
    private val searchKeywordKey = stringPreferencesKey("searchKeyword")

    suspend fun updateSearchedKeyword(newKeyword: String): List<String> {
        val previous = getSearchedKeywordKey()?.toConvertedList() ?: emptyList()
        // A lot of calculations
        val newHistories = (previous + newKeyword)
            .reversed()
            .distinct()
            .reversed()
            .toConvertedString()
        updateSearchedKeywordKey(newHistories)
        return newHistories.toConvertedList()
    }

    suspend fun getSearchedKeyword() = getSearchedKeywordKey()?.toConvertedList() ?: emptyList()

    suspend fun deleteSearchedKeyword(targetKeyword: String): List<String> {
        val previous = getSearchedKeywordKey()?.toConvertedList() ?: emptyList()
        val newHistories = previous.filter { it != targetKeyword }.toConvertedString()
        updateSearchedKeywordKey(newHistories)
        return newHistories.toConvertedList()
    }

    suspend fun initSearchedKeyword(): List<String> {
        updateSearchedKeywordKey("")
        return emptyList()
    }

    private suspend fun getSearchedKeywordKey() = context.searchHistoryDataStore.data
        .map { preferences ->
            preferences[searchKeywordKey]
        }.first()

    private suspend fun updateSearchedKeywordKey(searchKeyword: String) {
        context.searchHistoryDataStore.edit { preferences ->
            preferences[searchKeywordKey] = searchKeyword
        }
    }

    private fun List<String>.toConvertedString(): String {
        return this.toString().replace("[", "").replace("]", "")
    }

    private fun String.toConvertedList(): List<String> {
        return this.split(",")
            .filter { it.isNotBlank() }
            .map { it.trim() }
    }

}