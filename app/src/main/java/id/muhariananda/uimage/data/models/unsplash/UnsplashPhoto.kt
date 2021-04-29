package id.muhariananda.uimage.data.models.unsplash

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashPhoto(
    val id: String,
    val description: String?,
    val photoUrs: UnsplashPhotoUrls,
    val userUrl: UnsplashUser,
    val width: Int,
    val height: Int,
): Parcelable