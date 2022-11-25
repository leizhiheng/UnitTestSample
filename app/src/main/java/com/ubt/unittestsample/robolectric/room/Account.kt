package com.ubt.unittestsample.robolectric.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account(@PrimaryKey var id: Long) {
    var email: String? = null
    var name: String? = null
    var password: String? = null
    var addToDB = false
}
