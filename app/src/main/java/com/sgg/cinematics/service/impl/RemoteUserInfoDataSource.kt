package com.sgg.cinematics.service.impl

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.data.repository.impl.USER_COLLECTION
import com.sgg.cinematics.di.RemoteDataSource
import com.sgg.cinematics.service.UserInfoDataSource
import com.sgg.cinematics.utils.TAG_USER_CREATION_FLOW

@RemoteDataSource
class RemoteUserInfoDataSource(
        private val firestore: FirebaseFirestore = Firebase.firestore
) : UserInfoDataSource {
    override fun getUserInfo(uid: String) {
        TODO("Not yet implemented")
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