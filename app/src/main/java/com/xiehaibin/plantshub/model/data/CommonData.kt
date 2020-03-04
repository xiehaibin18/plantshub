package com.xiehaibin.plantshub.model.data

import android.os.Bundle

class CommonData private constructor() {
    companion object {
        private var INSTANCE: CommonData? = null
        fun getInstance() =
            INSTANCE ?: synchronized(this) {
                CommonData().also { INSTANCE = it }
            }
    }

    // baseUrl
//    private var baseUrl: String = "http://10.0.2.2:3000"
    var baseUrl: String = "http://192.168.0.105:3000"

    // 获取验证AccountToken的url
    fun getChkAccountTokenUrl() = baseUrl.plus("/api/CheckAccountToken")

    // 获取验证AccountToken的url
    fun getChkLoginUrl() = baseUrl.plus("/api/CheckLogin")

    // 获取验证AccountToken的url
    fun getUserRegisterUrl() = baseUrl.plus("/api/UserRegister")

    // 获取pictureRecognition的url
    fun getPictureRecognitionUrl() = baseUrl.plus("/api/pictureRecognition")

    // 获取LocationData的url
    fun getLocationDataUrl() = baseUrl.plus("/api/adminGetLocationData")

    // 获取OverviewData的url
    fun getOverviewDataUrl() = baseUrl.plus("/api/UserGetData")

    // 获取UserInfoData的url
    fun getUserInfoUrl() = baseUrl.plus("/api/UserGetData")

    // 获取UserMessageData的url
    fun getUserMessageUrl() = baseUrl.plus("/api/UserGetData")

    // 获取UserFavoriteData的url
    fun getUserFavoriteUrl() = baseUrl.plus("/api/UserGetData")

    // 获取GetListData的url
    fun getListDataUrl() = baseUrl.plus("/api/UserGetData")

    // accountToken
    private var accountToken: String = ""

    fun setAccountToken(value: String) {
        accountToken = value
    }

    fun getAccountToken(): String = accountToken
    // 照片路径
    private var path: String = ""

    fun setPath(value: String) {
        path = value
    }

    fun getPath(): String = path
    // 用户姓名
    private var userName: String = ""

    fun setUserName(value: String) {
        userName = value
    }

    fun getUserName(): String = userName
    // 用户头像
    private var userAvatar: String = ""

    fun setUserAvatar(value: String) {
        userAvatar = value
    }

    fun getUserAvatar(): String = userAvatar
    // Dialog
    private var isDialog: Boolean = false

    fun setIsDialog(value: Boolean) {
        isDialog = value
    }

    fun getIsDialog(): Boolean = isDialog
    // router
    private var router: Int = 0

    fun setRouter(value: Int) {
        router = value
    }

    fun getRouter(): Int = router
    // routerData
    private lateinit var routerData: Bundle

    fun setRouterData(value: Bundle) {
        routerData = value
    }

    fun getRouterData(): Bundle = routerData

    // 获取OverviewData的url
    private lateinit var overviewDataType: String

    fun setOverviewDataType(value: String) {
        overviewDataType = value
    }

    fun getOverviewDataType() = overviewDataType
}