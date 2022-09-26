package com.project.test.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Model for the Entry object.
 */
@Parcelize
data class Entry(
    val title: String,
    val date_taken: String,
    val description: String,
    val published: String,
    val tags: String,
    val author: String,
    val author_id: String,
    val media: FlickrMedia,
) : Parcelable