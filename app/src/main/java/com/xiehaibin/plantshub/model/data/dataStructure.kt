package com.xiehaibin.plantshub.model.data

import com.google.gson.annotations.SerializedName

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

data class LocationData(
    val hits: Array<LocationDataItem>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocationData

        if (!hits.contentEquals(other.hits)) return false

        return true
    }

    override fun hashCode(): Int {
        return hits.contentHashCode()
    }
}

data class LocationDataItem(
    @SerializedName("uid") val location_uid: String,
    @SerializedName("name") val location_name: String
)

data class AllPlantsData(
    val err_code: Int,
    val msg: String?,
    val data: Array<AllPlantsDataItem>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AllPlantsData

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

data class AllPlantsDataItem(
    val plants_uid: Int,
    val plants_name: String?,
    val plants_introduction: String?,
    val plants_picture: String?,
    val plants_distributions_uid: Any?,
    val plants_like: Int?
)