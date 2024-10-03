package com.example.comunicaa.domain.models.user

import android.os.Parcelable
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.Exclude
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    @SerializedName("displayName")
    val displayName: String? = "",
    @SerializedName("isAnonymous")
    val isAnonymous: Boolean? = false,
    @SerializedName("photoUrl")
    val photoUrl: String? = "",
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("isEmailVerified")
    val isEmailVerified: Boolean? = false,
    @SerializedName("phoneNumber")
    val phoneNumber: String? = "",
    @SerializedName("providerId")
    val providerId: String? = "",
    @SerializedName("tenantId")
    val tenantId: String? = "",
    @SerializedName("uid")
    val uid: String? = ""
) : Parcelable {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "displayName" to (displayName ?: ""),
            "isAnonymous" to (isAnonymous ?: false),
            "photoUrl" to (photoUrl ?: ""),
            "email" to (email ?: ""),
            "isEmailVerified" to (isEmailVerified ?: ""),
            "phoneNumber" to (phoneNumber ?: ""),
            "providerId" to (providerId ?: ""),
            "tenantId" to (tenantId ?: ""),
            "uid" to (uid ?: ""),
        )
    }
}

fun FirebaseUser?.toUserModel(): UserModel? {
    val user = this?.let {
        UserModel(
            displayName = this.displayName,
            email = this.email,
            isAnonymous = this.isAnonymous,
            photoUrl = this.photoUrl.toString(),
            isEmailVerified = this.isEmailVerified,
            phoneNumber = this.phoneNumber,
            providerId = this.providerId,
            tenantId = this.tenantId,
            uid = this.uid,
        )
    }

    return user
}
