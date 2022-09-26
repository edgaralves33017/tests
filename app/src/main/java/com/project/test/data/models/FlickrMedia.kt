package com.project.test.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Model for the "media" portion of the flickr api response
 */
@Parcelize
class FlickrMedia(
    val m: String
) : Parcelable