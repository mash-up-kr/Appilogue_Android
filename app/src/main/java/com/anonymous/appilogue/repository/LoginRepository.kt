package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.*
import com.anonymous.appilogue.network.api.AuthApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LoginRepository @Inject constructor(private val authApi: AuthApi) {
    fun sendCertificationEmail(emailModel: SendEmailModel): Single<SendEmailResult> {
        return authApi.sendCertificationToEmail(emailModel)
    }

    fun verifyCertificationNumber(postData: PostVerifyCode): Single<VerifyCode> {
        return authApi.verifyCertificationNumber(postData)
    }

    fun validateNickname(nickname: Map<String, String>): Single<ValidateNickname> {
        return authApi.validateNickname(nickname)
    }

    fun postToServerUserData(userData: SignUpModel): Single<SignUpResult> {
        return authApi.postToServerUserData(userData)
    }

    fun loginWithEmailPassword(loginModelData: LoginModel): Single<TokenModel> {
        return authApi.loginWithEmailPassword(loginModelData)
    }

    fun updatePassword(updatePasswordModel: UpdatePasswordModel): Single<UpdatePasswordResult> {
        return authApi.updatePassword(updatePasswordModel)
    }
}
