package com.ubt.unittestsample.robolectric.net

import android.os.Parcel
import android.os.Parcelable

data class Repository(var id: Long): Parcelable {
    var name: String? = null
    var description: String? = null
    var forks = 0
    var watchers = 0

    var stars = 0
    var language: String? = null
    var homepage: String? = null
    var owner: User? = null
    var fork = false

    constructor(parcel: Parcel) : this(parcel.readLong()) {
        name = parcel.readString()
        description = parcel.readString()
        forks = parcel.readInt()
        watchers = parcel.readInt()
        stars = parcel.readInt()
        language = parcel.readString()
        homepage = parcel.readString()
        owner = parcel.readParcelable(User::class.java.classLoader)
        fork = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(forks)
        parcel.writeInt(watchers)
        parcel.writeInt(stars)
        parcel.writeString(language)
        parcel.writeString(homepage)
        parcel.writeParcelable(owner, flags)
        parcel.writeByte(if (fork) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Repository> {
        override fun createFromParcel(parcel: Parcel): Repository {
            return Repository(parcel)
        }

        override fun newArray(size: Int): Array<Repository?> {
            return arrayOfNulls(size)
        }
    }

}