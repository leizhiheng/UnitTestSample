package com.ubt.unittestsample.roboletric.room

import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.google.gson.Gson
import com.ubt.unittestsample.robolectric.room.Account
import com.ubt.unittestsample.robolectric.room.AccountDao
import com.ubt.unittestsample.robolectric.room.AccountDatabase
import com.ubt.unittestsample.robolectric.room.AccountRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.kotlin.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog
import java.util.concurrent.CountDownLatch


/**
 * 目标：Room数据库测试
 *
 * 测试意义：
 * 1、快速验证数据库操作正常与否
 *
 * 问题：
 * 1、要使用RobolectricTestRunner,否则会报错：No instrumentation registered! Must run under a registering instrumentation. 使用Robolectric框架应该都要申明使用RobolectricTestRunner
 * 2、cannot find implementation for com.ubt.unittestsample.robolectric.room.AccountDatabase. AccountDatabase_Impl does not exist
 * 解决：
 * 增加以下依赖：
 *     implementation "androidx.room:room-ktx:$room_version"
 *     kapt "androidx.room:room-compiler:$room_version"
 * 3、关于异步任务的测试：
 * 测试方式：因为异步任务的执行和验证是在不同线程执行的，为了保证我们的验证是在异步执行完成之后执行，我们需要将异步任务的执行和验证做成同步的。
 * 比如使用CountDownLatch。await(),Lock.Await()方法等；或者
 */
@RunWith(RobolectricTestRunner::class)
class RoomTest {
    companion object {
        const val TAG = "RoomTest"
    }

    lateinit var spyAccountDao: AccountDao
    lateinit var accountDao: AccountDao
    lateinit var latch: CountDownLatch

    private lateinit var repository: AccountRepository

    @Before
    fun setup() {
        ShadowLog.stream = System.out
        latch = CountDownLatch(1)
        //在Robolectric 4.0中获取context,需要使用androidx.test:core-ktx 库中的ApplicationProvider.getApplicationContext()
        accountDao =
            AccountDatabase.getInstance(ApplicationProvider.getApplicationContext()).accountDao()
        spyAccountDao = spy(accountDao)

        repository = mock()
    }

    @Test
    fun queryAllAccount_MockDao_Test() {

        val mockDao = mock<AccountDao>()

        var account1 = Account(id = 1001).apply {
            name = "lzh"
            email = "zhiheng.lei@163.com"
        }
        var account2 = Account(id = 1002).apply {
            name = "blue"
            email = "blue.lei@163.com"
        }
        //先插入数据
        operateAccountOnSubThread {
            mockDao.addAccount(account1)
            mockDao.addAccount(account2)
            latch.countDown()
            Log.i(TAG, "thread working add account")
        }
        latch.await()

        val accounts = mutableListOf<Account>()

        //mock对象不会真实执行方法，所以这里Stubbing queryAllAccount()方法，返回 arrayOf(account1, account2)。省略这个Stubbing queryAllAccount()会返回null
        `when`(mockDao.queryAllAccount()).thenReturn(arrayOf(account1, account2))

        //查询数据
        operateAccountOnSubThread {
            mockDao.queryAllAccount().apply {
                accounts.addAll(this)
            }
            latch.countDown()
        }

        latch.await()
        Log.i(TAG, "queryAllAccountTest accounts: ${Gson().toJson(accounts)}")
        assertTrue(accounts.size > 0)
        //执行两次addAccount()
        verify(mockDao, times(2)).addAccount(isA())
        //执行一次queryAllAccount()
        verify(mockDao).queryAllAccount()
    }

    @Test
    fun queryAllAccountTest() {

        var account1 = Account(id = 1001).apply {
            name = "lzh"
            email = "zhiheng.lei@163.com"
        }
        var account2 = Account(id = 1002).apply {
            name = "blue"
            email = "blue.lei@163.com"
        }
        //先插入数据
        operateAccountOnSubThread {
            spyAccountDao.addAccount(account1)
            spyAccountDao.addAccount(account2)
            latch.countDown()
            Log.i(TAG, "thread working add account")
        }
        latch.await()

        val accounts = mutableListOf<Account>()
        //查询数据
        operateAccountOnSubThread {
            spyAccountDao.queryAllAccount().apply {
                accounts.addAll(this)
                Log.i(TAG, "thread working query all account")
                latch.countDown()
            }
        }

        latch.await()
        Log.i(TAG, "queryAllAccountTest accounts: ${Gson().toJson(accounts)}")
        assertTrue(accounts.size > 0)
        //执行两次addAccount()
        verify(spyAccountDao, times(2)).addAccount(isA())
        //执行一次queryAllAccount()
        verify(spyAccountDao).queryAllAccount()
    }

    /**
     * 使用CountdownLatch来测试异步任务
     */
    @Test
    fun insertAccountTest() {
        var account1 = Account(id = 1001).apply {
            name = "lzh"
            email = "zhiheng.lei@163.com"
        }
        var account2 = Account(id = 1002).apply {
            name = "blue"
            email = "blue.lei@163.com"
        }

        //将真实的对象转换成Spy，这样才可以使用verify来验证
        var spy = spy(accountDao)
        //这样处理并不好，
        operateAccountOnSubThread {
            spy.addAccount(account1)
            spy.addAccount(account2)
            latch.countDown()
            Log.i(TAG, "thread working")
        }

        latch.await()
        Log.i(TAG, "thread finish")
        verify(spy, times(2)).addAccount(isA())
    }

    private fun operateAccountOnSubThread(execute: () -> Unit) {
        Thread {
            kotlin.run {
                execute.invoke()
            }
        }.start()
    }
}