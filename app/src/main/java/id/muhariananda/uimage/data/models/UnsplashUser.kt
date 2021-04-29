package id.muhariananda.uimage.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashUser(
    val id: String,
    val name: String,
    val username: String,
    val profileImage: ProfileImageUrl
) : Parcelable {
    val attributionUrls get() = "https://unsplash.com/$username?utm_source=Uimage&utm_medium=referral"
}