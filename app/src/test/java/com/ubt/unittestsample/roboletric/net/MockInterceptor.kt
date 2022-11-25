package com.ubt.unittestsample.roboletric.net

import com.ubt.unittestsample.utils.FileUtil
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class MockInterceptor(var responseJsonPath: String = ""): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var responseString = createResponseBody(chain)?: ""

        return Response.Builder()
            .code(200)
            .message(responseString)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_0)
            .body(ResponseBody.create(MediaType.parse("application/json"), responseString.toByteArray()))
            .addHeader("content-type", "application/json")
            .build()
    }

    /**
     * 读文件获取json字符串，生成ResponseBody
     *
     * @param chain
     * @return
     */
    private fun createResponseBody(chain: Interceptor.Chain): String? {
        var responseString: String? = null
        val uri = chain.request().url()
        val path = uri.url().path

        if (path.matches(Regex("^(/users/)+[^/]*+(/repos)$"))) { //匹配/users/{username}/repos
            responseString = getResponseString("users_repos.json")
        } else if (path.matches(Regex("^(/users/)+[^/]+(/following)$"))) { //匹配/users/{username}/following
            responseString = getResponseString("users_following.json")
        } else if (path.matches(Regex("^(/users/)+[^/]*+$"))) { //匹配/users/{username}
            responseString = getResponseString("users.json")
        }
        return responseString
    }

    private fun getResponseString(fileName: String): String {
        return FileUtil.readFile(responseJsonPath + fileName, "UTF-8").toString()
    }
}