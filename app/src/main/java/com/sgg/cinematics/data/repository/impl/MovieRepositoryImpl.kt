package com.sgg.cinematics.data.repository.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.utils.MovieListFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val MOVIE_COLLECTION = "movies"

class MovieRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
        MovieRepository {
    override fun getAllMovies(movieListFilter: MovieListFilter): Flow<List<MovieModel>> {
        return when (movieListFilter) {
            MovieListFilter.TRENDING  -> getTrending()
            MovieListFilter.TOP_RATED -> getTopRated()
            MovieListFilter.LATEST    -> getLatest()
        }
    }


    override suspend fun getMovie(id: Int): MovieModel? {
        return firestore.collection(MOVIE_COLLECTION)
            .whereEqualTo("id", id)
            .get()
            .await()
            .first()
            .toObject<MovieModel>()
    }


    private fun getTopRated(): Flow<List<MovieModel>> {
        return firestore.collection(MOVIE_COLLECTION)
            .orderBy("ratingNote", Query.Direction.DESCENDING)
            .dataObjects()
    }

    private fun getTrending(): Flow<List<MovieModel>> {
        return firestore.collection(MOVIE_COLLECTION)
            .dataObjects()
    }

    private fun getLatest(): Flow<List<MovieModel>> {
        TODO()
    }
}