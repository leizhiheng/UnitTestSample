package com.ubt.unittestsample.robolectric.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Account::class], version = 1, exportSchema = false)
abstract class AccountDatabase: RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {
        @Volatile
        private var accountDatabase: AccountDatabase? = null
        private const val DATABASE_NAME = "account_database"

        fun getInstance(context: Context): AccountDatabase {
            return accountDatabase?: synchronized(this) {
                accountDatabase?: Room.databaseBuilder(context.applicationContext, AccountDatabase::class.java, DATABASE_NAME).build().also {
                    accountDatabase = it
                }
            }
        }
    }
}