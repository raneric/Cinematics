package com.sgg.cinematics.data.repository.impl

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.data.repository.UserInfoRepository
import com.sgg.cinematics.utils.TAG_USER_CREATION_FLOW
import javax.inject.Inject

const val USER_COLLECTION = "userInfo"


class UserInfoRepositoryImpl @Inject constructor(
        val firestore: FirebaseFirestore
) : UserInfoRepository {
    override fun getUserInfo(uid: String) {
        TODO("Not yet implemented")
    }

    //FIXE dada upload
    override fun addUserInfo(userInfo: UserModel) {
        firestore.collection(USER_COLLECTION)
            .add(userInfo)
            .addOnFailureListener {
                Log.d(TAG_USER_CREATION_FLOW, it.message.toString())
            }
            .addOnSuccessListener { documentReference ->
                Log.d(TAG_USER_CREATION_FLOW, "add user success with uid ${documentReference.id}")
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