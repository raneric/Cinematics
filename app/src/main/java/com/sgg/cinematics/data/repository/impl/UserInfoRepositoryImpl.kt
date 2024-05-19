package com.sgg.cinematics.data.repository.impl

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.data.repository.UserInfoRepository
import com.sgg.cinematics.utils.TAG_USER_INFO_FLOW
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val USER_COLLECTION = "userInfo"


class UserInfoRepositoryImpl @Inject constructor(
        private val firestore: FirebaseFirestore
) : UserInfoRepository {
    override suspend fun getUserInfo(uid: String): UserModel {
        return firestore.collection(USER_COLLECTION)
            .whereEqualTo("id", uid)
            .get()
            .addOnFailureListener {
                Log.d(TAG_USER_INFO_FLOW, it.message.toString())
            }
            .await()
            .first()
            .toObject<UserModel>()
    }

    override fun addOrUpdateUserInfo(userInfo: UserModel) {
        firestore.collection(USER_COLLECTION)
            .document(userInfo.id!!)
            .set(userInfo)
            .addOnFailureListener {
                Log.d(TAG_USER_INFO_FLOW, it.message.toString())
            }
            .addOnCanceledListener {
                Log.d(TAG_USER_INFO_FLOW, "add user canceled")
            }
    }

    override fun deleteUserInfo(uid: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getWatchList(): Flow<List<MovieModel>> {
        TODO("Not yet implemented")
    }

    override fun addToWatchList(movieModel: MovieModel) {
        TODO("Not yet implemented")
    }

}