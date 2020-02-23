package com.xiehaibin.plantshub.model.data

data class checkLoginData(
    val err_code: Int,
    val message: String?,
    val account_token: String?
)