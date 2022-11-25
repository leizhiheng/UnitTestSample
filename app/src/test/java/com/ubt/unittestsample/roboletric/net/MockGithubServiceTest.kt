package com.ubt.unittestsample.roboletric.net

import android.util.Log
import com.google.gson.Gson
import com.ubt.unittestsample.robolectric.net.GithubService
import okhttp3.OkHttpClient
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 目的：模拟网络请求
 * 测试意义：
 * 1、模拟特定请求结果，验证软件在特定请求结果下的处理逻辑。比如请求正常、失败、超时等特殊情况的处理逻辑。而真实请求的结果往往不好确定。
 *
 * 模拟方式：
 * 1、通过okhttp的拦截器(interceptor)拦截请求，根据请求路径拦截请求不发送，而是直接返回我们自定义好的response json字符串
 *
 * 问题：
 * 1、网络请求的一步回调怎么测试
 * 方案：使用Mockito的Captor来做异步回调测试，后续待研究
 */
@RunWith(RobolectricTestRunner::class)
class MockGithubServiceTest {
    companion object {
        const val TAG = "MockGithubServiceTest"
    }

    private val JSON_ROOT_PATH = "/json/"
    var responseJsonPath = ""
    lateinit var githubService: GithubService

    @Before
    fun setup() {
        //输出日志
        ShadowLog.stream = System.out

        responseJsonPath = javaClass.getResource(JSON_ROOT_PATH)?.toURI()?.path ?: ""
        //创建OkHttpClient，并设置拦截器
        val okHttpClient = OkHttpClient.Builder().addInterceptor(MockInterceptor(responseJsonPath)).build()
        //创建Retrofit对象
        val retrofit = Retrofit.Builder()
            .baseUrl(GithubService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        githubService = retrofit.create(GithubService::class.java)
    }

    @Test
    fun mockPublicRepositories() {
        val call = githubService.publicRepositories("geniusmart")
        val response = call.execute()
        val repositories = response.body()
        Log.i(TAG, "mockPublicRepositories repositories: ${Gson().toJson(repositories)}")
        assertTrue((repositories?.size ?: 0) > 0)
    }

    @Test
    fun mockFollowingUsers() {
        val call = githubService.followingUser("geniusmart")
        val response = call.execute()
        val users = response.body()?: listOf()
        Log.i(TAG, "mockFollowingUsers, users: ${Gson().toJson(users)}")
        assertTrue(users.isNotEmpty())
    }
}