package com.sgg.cinematics.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.sgg.cinematics.data.repository.PreferencesKeys.IS_LIST_VIEW
import com.sgg.cinematics.utils.MovieListUiMode
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UiStatePreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    val movieListUiModeFlow = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }
            .map { pref ->
                val isList = pref[IS_LIST_VIEW] ?: true
                if (isList) MovieListUiMode.ListView else MovieListUiMode.CarouselView
            }


    suspend fun updateUiState(movieListUiMode: MovieListUiMode) {
        dataStore.edit { currentPref ->
            currentPref[IS_LIST_VIEW] = movieListUiMode is MovieListUiMode.ListView
        }
    }

}

private object PreferencesKeys {
    val IS_LIST_VIEW = booleanPreferencesKey("is_list_view")
}