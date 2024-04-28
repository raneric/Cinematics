package com.sgg.cinematics.data.repository.impl

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.data.repository.UserInfoRepository
import com.sgg.cinematics.utils.TAG_USER_CREATION_FLOW
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
            .await()
            .first()
            .toObject<UserModel>()
    }

    override fun addUserInfo(userInfo: UserModel) {
        firestore.collection(USER_COLLECTION)
            .add(userInfo)
            .addOnFailureListener {
                Log.d(TAG_USER_CREATION_FLOW, it.message.toString())
            }
            .addOnCanceledListener {
                Log.d(TAG_USER_CREATION_FLOW, "add user canceled")
            }
    }

    override fun updateUserInfo(userInfo: UserModel) {
        TODO("Not yet implemented")
    }

    override fun deleteUserInfo(uid: String) {
        TODO("Not yet implemented")
    }
}