package com.sgg.cinematics.data

import com.sgg.cinematics.data.repository.PreferenceRepository
import com.sgg.cinematics.utils.MovieListUiMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FakePreferenceRepository @Inject constructor() : PreferenceRepository {

    private val fakeDateStore = MutableStateFlow<MovieListUiMode>(MovieListUiMode.ListView)

    override val movieListUiModeFlow: Flow<MovieListUiMode> = fakeDateStore

    override suspend fun updateListUiMode(movieListUiMode: MovieListUiMode) {
        fakeDateStore.emit(movieListUiMode)
    }

}
