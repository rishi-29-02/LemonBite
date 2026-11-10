package com.rm.lemonbite.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

@Singleton
class UserPreferencesRepository
@Inject
constructor(
    private val dataStore: DataStore<Preferences>,
) {
    companion object {
        val USER_FIRST_NAME = stringPreferencesKey("user_first_name")
        val USER_EMAIL = stringPreferencesKey("user_email")

        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")

        val COURSE_PREFS = stringSetPreferencesKey("course_prefs")
    }

    val preferences: Flow<Preferences> =
        dataStore.data.catch { exception ->
            if (exception is Exception) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }

    val userName: Flow<String> =
        preferences.map { prefs ->
            prefs[USER_FIRST_NAME] ?: ""
        }

    val userEmail: Flow<String> =
        preferences.map { prefs ->
            prefs[USER_EMAIL] ?: ""
        }

    val onBoardingCompleted: Flow<Boolean> =
        preferences.map { prefs ->
            prefs[ONBOARDING_COMPLETED] ?: false
        }

    val coursesPrefs: Flow<Set<String>> =
        preferences.map { prefs ->
            prefs[COURSE_PREFS] ?: emptySet()
        }

    suspend fun saveUserData(firstName: String, email: String) {
        dataStore.edit { preferences ->
            preferences[USER_FIRST_NAME] = firstName
            preferences[USER_EMAIL] = email
        }
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = completed
        }
    }

    suspend fun setPrefs(prefs: Set<String>) {
        dataStore.edit { preferences ->
            preferences[COURSE_PREFS] = prefs
        }
    }
}
