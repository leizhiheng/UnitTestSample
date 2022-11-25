package com.ubt.unittestsample.robolectric.net

import android.os.Parcel
import android.os.Parcelable

data class User(var id: Long): Parcelable {
    var name: String? = null
    var url: String? = null
    var email: String? = null
    var login: String? = null
    var location: String? = null
    var avatarUrl: String? = null

    constructor(parcel: Parcel) : this(parcel.readLong()) {
        name = parcel.readString()
        url = parcel.readString()
        email = parcel.readString()
        login = parcel.readString()
        location = parcel.readString()
        avatarUrl = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeString(email)
        parcel.writeString(login)
        parcel.writeString(location)
        parcel.writeString(avatarUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}
