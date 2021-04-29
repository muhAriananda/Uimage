package id.muhariananda.uimage.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashPhotoUrls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
): Parcelable