package com.sgg.cinematics.data.datasource.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.sgg.cinematics.data.datasource.MovieDataSource
import com.sgg.cinematics.data.model.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val MOVIE_COLLECTION = "movies"

class MovieDataSourceImpl @Inject constructor(private val firestore: FirebaseFirestore) :
        MovieDataSource {
    override fun getAllMovies(): Flow<List<MovieModel>> {
        return firestore.collection(MOVIE_COLLECTION)
                .dataObjects()
    }

    override fun getTopRatedMovies(): Flow<List<MovieModel>> {
        return firestore.collection(MOVIE_COLLECTION)
                .orderBy("ratingNote", Query.Direction.DESCENDING)
                .dataObjects()
    }

    override suspend fun getMovie(id: String): MovieModel? {
        return firestore.collection(MOVIE_COLLECTION)
                .document(id)
                .get()
                .await()
                .toObject()
    }
}