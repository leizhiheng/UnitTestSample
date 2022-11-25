package com.ubt.unittestsample.roboletric.net

import android.util.Log
import com.google.gson.Gson
import com.ubt.unittestsample.robolectric.net.GithubService
import com.ubt.unittestsample.robolectric.net.Repository
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog
import retrofit2.Call

/**
 * 目的：接口真实请求测试
 * 测试意义：
 * 1、检验网络接口的稳定性
   2、检验部分响应结果数据的完整性（如非空验证）
   3、方便开发阶段的联调（通过UT联调的效率远高于run app后联调）
 *
 * 注意：
 * 1、这里一定要使用RobolectricTestRunner，否则对于一些Shadow*组件的设置不生效。例如下面的ShadowLog会提示：Method i in android.util.Log not mocked
 * 2、Robolectric框架使用4.4版本，4.3.1的版本执行网络请求时有一个问题：https://stackoverflow.com/questions/60472729/robolectric-test-that-uses-okhttp-for-real-http-requests-throws-java-lang-nullpo/60472730#60472730
 */
@RunWith(RobolectricTestRunner::class)
class GitHubServiceTest {
    companion object {
        const val TAG = "GitHubServiceTest"
    }

    lateinit var githubService: GithubService

    @Before
    fun setup() {
        //设置ShadowLog,之后可以直接使用Log.x()
        ShadowLog.stream = System.out
        githubService = GithubService.Factory.create()
//        System.setProperty("javax.net.ssl.trustStoreType", "JKS")
        System.setProperty("javax.net.ssl.trustStore", "NONE")
    }

    @Test
    fun publicRepositories() {

        var call: Call<List<Repository>> = githubService.publicRepositories("geniusmart")
        var execute = call.execute()

        var repositories = execute.body()

        //打印请求结果验证
        Log.i(TAG, Gson().toJson(repositories))
        assertTrue(repositories?.isNotEmpty()?: false)
        assertNotNull(repositories?.get(0)?.name)
        Log.i(TAG, "${repositories?.get(0)?.name}")
    }
}