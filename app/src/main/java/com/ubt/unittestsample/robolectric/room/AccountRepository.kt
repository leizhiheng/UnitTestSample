package com.ubt.unittestsample.robolectric.room

import android.util.Log
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update

class AccountRepository(private val accountDao: AccountDao) {
    companion object {
        const val TAG = "AccountRepository"
    }
    fun addAccount(account: Account?) {
        Thread {
            kotlin.run {
                accountDao.addAccount(account!!)
                account.addToDB = true
                println("doing addAccount")
            }
        }.start()
    }

    fun deleteAccount(accounts: Array<Account>) {
        accountDao.deleteAccount(accounts)
    }

    fun updateAccount(account: Account) {
        accountDao.updateAccount(account)
    }

    fun queryAccount(id: Long): Account {
        return accountDao.queryAccount(id)
    }

    fun queryAllAccount(): Array<Account> {
        return accountDao.queryAllAccount()
    }
}