package id.muhariananda.uimage.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashUser(
    val id: String,
    val name: String,
    val username: String,
    @SerializedName("profile_image")
    val urls: ProfileImageUrl
) : Parcelable {
    val attributionUrls get() = "https://unsplash.com/$username?utm_source=Uimage&utm_medium=referral"
}