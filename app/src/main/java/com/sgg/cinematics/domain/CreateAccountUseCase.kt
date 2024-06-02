package com.sgg.cinematics.domain

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.sgg.cinematics.data.model.AuthData
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.data.repository.UserInfoRepository
import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.utils.currentDateAsString
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
        when (val createdUser = authService.createUser(authData)) {
            null -> {
                throw Exception("Failed to create user")
            }

            else -> {
                val url = uploadAndGetUrl(pictureProfileUri, createdUser.uid)
                var fullUserData = userInfo.copy(id = createdUser.uid, pictureUrl = url)
                userInfoRepository.addOrUpdateUserInfo(fullUserData)
            }
        }
    }

    private suspend fun uploadAndGetUrl(
        pictureUri: Uri,
        uid: String
    ): String? {
        var url: String? = null
        if (pictureUri != Uri.EMPTY) {
            try {
                val ref = firebaseStorage.reference.child(makeUserFilePath(uid))
                ref.putFile(pictureUri)
                    .await()
                url = ref.downloadUrl.await()
                    .toString()
            } catch (e: Exception) {
                throw Exception("Failed to upload profile picture")
            }
        }
        return url
    }
}

private fun makeUserFilePath(uid: String): String {
    return "$USER_FILES_ROOT_FOLDER/$uid/${
        LocalDateTime.now()
            .currentDateAsString()
    }"
}