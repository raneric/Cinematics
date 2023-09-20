package com.example.cinematics.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.cinematics.data.repository.PreferencesKeys.IS_LIST_VIEW
import com.example.cinematics.utils.UiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UiStatePreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>){

    val uiStateFlow = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }
            .map { pref ->
                val isList = pref[IS_LIST_VIEW] ?: true
                if (isList) UiState.ListView else UiState.CarouselView
            }


    suspend fun updateUiState(uiState: UiState) {
        dataStore.edit { currentPref ->
            currentPref[IS_LIST_VIEW] = uiState is UiState.ListView
        }
    }

}

private object PreferencesKeys {
    val IS_LIST_VIEW = booleanPreferencesKey("is_list_view")
}