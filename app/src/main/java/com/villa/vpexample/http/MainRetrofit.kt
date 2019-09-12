package com.villa.vpexample.http

import com.exmple.corelib.http.retrofit.RetrofitFactory
import okhttp3.Request

/**
 * FileName: com.beijing.zhagen.meiqi.http.retrofit.MainRetrofit.java
 * Author: mouxuefei
 * date: 2018/3/20
 * version: V1.0
 * desc:
 */

object MainRetrofit : RetrofitFactory<MainApi>() {
    override fun getHeader(builder: Request.Builder): Request.Builder {
        return builder.addHeader("token","1233333333333333")
    }
    override fun getBaseUrl()="https://www.wanandroid.com/"
    override fun getApiService(): Class<MainApi> {
        return MainApi::class.java
    }

    override fun getToken() = ""
}