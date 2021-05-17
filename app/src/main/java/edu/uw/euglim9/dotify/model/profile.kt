package edu.uw.euglim9.dotify.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val username: String,
    val firstName: String,
    val lastName: String,
    val hasNose: Boolean,
    val platform: Long,
    val profilePicURL: String
): Parcelable