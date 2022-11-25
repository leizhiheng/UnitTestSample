package com.ubt.unittestsample.robolectric.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface AccountDao {
    @Insert(onConflict = REPLACE)
    fun addAccount(account: Account)

    @Delete
    fun deleteAccount(accounts: Array<Account>)

    @Update
    fun updateAccount(account: Account)

    @Query("SELECT * FROM account WHERE id = :id")
    fun queryAccount(id: Long): Account

    @Query("SELECT * FROM account")
    fun queryAllAccount(): Array<Account>
}