package com.alreadyoccupiedseat.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.onboardingDataStore: DataStore<Preferences> by preferencesDataStore(name = "onboarding")

class OnboardingDataStore @Inject constructor(@ApplicationContext private val context: Context) {
    private val whetherIsFinishedKey = booleanPreferencesKey("whetherIsFinishedKey")

    suspend fun updateWhetherOnboardingIsFinished(isFinished: Boolean) {
        context.onboardingDataStore.edit { preferences ->
            preferences[whetherIsFinishedKey] = isFinished
        }
    }

    suspend fun getWhetherIsFinished() = context.onboardingDataStore.data
        .map { preferences ->
            preferences[whetherIsFinishedKey]
        }.first() ?: false

}