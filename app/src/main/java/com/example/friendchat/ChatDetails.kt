package com.example.friendchat

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize

@VersionedParcelize
data class ChatDetails(
    val name : String,
    val uid : String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(uid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChatDetails> {
        override fun createFromParcel(parcel: Parcel): ChatDetails {
            return ChatDetails(parcel)
        }

        override fun newArray(size: Int): Array<ChatDetails?> {
            return arrayOfNulls(size)
        }
    }

}
