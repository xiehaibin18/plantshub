package com.xiehaibin.plantshub.model.data

class CommonData private constructor(){
    companion object {
        private var INSTANCE : CommonData?=null
        fun getInstance() =
            INSTANCE?: synchronized(this) {
                CommonData().also { INSTANCE = it }
            }
    }

    // baseUrl
    private var baseUrl: String = "http://10.0.2.2:3000"
    // 获取验证AccountToken的url
    fun getChkAccountTokenUrl() = baseUrl.plus("/api/CheckAccountToken")
    // 获取验证AccountToken的url
    fun getChkLoginUrl() = baseUrl.plus("/api/CheckLogin")
    // 获取验证AccountToken的url
    fun getUserRegisterUrl() = baseUrl.plus("/api/UserRegister")

    private var accountToken: String = ""

    fun setAccountToken(value: String) {
        accountToken = value
    }

    fun getAccountToken(): String = accountToken

}