package com.sgg.cinematics.domain

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.sgg.cinematics.data.model.AuthData
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.data.repository.UserInfoRepository
import com.sgg.cinematics.service.AuthService
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

const val USER_FILES_ROOT_FOLDER = "userFiles"

@Singleton
class CreateAccountUseCase @Inject constructor(
        private val userInfoRepository: UserInfoRepository,
        private val authService: AuthService,
        private val firebaseStorage: FirebaseStorage
) {

    suspend operator fun invoke(
            userInfo: UserModel,
            authData: AuthData,
            pictureProfileUri: Uri
    ) {
        authService.createUser(authData)
            ?.let {
                val url = uploadAndGetUrl(pictureProfileUri, it.uid)
                val fullUserData = userInfo.copy(id = it.uid, pictureUrl = url)
                userInfoRepository.addUserInfo(fullUserData)
            }
    }

    private suspend fun uploadAndGetUrl(
            pictureUri: Uri,
            uid: String
    ): String {
        val ref = firebaseStorage.reference.child("$USER_FILES_ROOT_FOLDER/$uid/${
            LocalDateTime.now()
                .currentDateAsString()
        }")
        ref.putFile(pictureUri)
            .await()
        val downloadUrl = ref.downloadUrl.await()
        return downloadUrl.toString()
    }
}

private fun FirebaseUser.combineWithUserModel(userModel: UserModel): UserModel {
    return userModel.copy(id = uid)
}

private fun LocalDateTime.currentDateAsString(): String {
    return "${this.dayOfMonth}${this.monthValue}${this.year}${this.hour}${this.minute}${this.second}"
}