package com.alreadyoccupiedseat.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.onboardingDataStore: DataStore<Preferences> by preferencesDataStore(name = "onboarding")

class OnboardingDataStore(private val context: Context) {
    private val whetherIsFinishedKey = stringPreferencesKey("whetherIsFinishedKey")

    private suspend fun updateWhetherOnboardingIsFinished(accessToken: String) {
        context.onboardingDataStore.edit { preferences ->
            preferences[whetherIsFinishedKey] = accessToken
        }
    }

    suspend fun getWhetherIsFinished() = context.onboardingDataStore.data
        .map { preferences ->
            preferences[whetherIsFinishedKey]
        }.first()

}