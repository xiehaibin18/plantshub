package com.xiehaibin.plantshub.model.data

data class CheckLoginData(
    val err_code: Int,
    val message: String?,
    val account_token: String?
)

data class CheckAccountTokenData(
    val err_code: Int,
    val message: String
)

data class UserRegisterData(
    val err_code: Int,
    val message: String?,
    val account_token: String?
)

data class PictureRecognitionData(
    val code: Int,
    val data: String?,
    val err: String?
)