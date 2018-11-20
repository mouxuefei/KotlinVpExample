package com.exmple.corelib.http.retrofit

import com.exmple.corelib.http.constant.URLConstant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * FileName: com.mou.demo.http.retrofit.RetrofitFactory.java
 * Author: mouxuefei
 * date: 2017/12/22
 * version: V1.0
 * desc:
 */
abstract class RetrofitFactory<T> {
    private val time_out: Long = 15//超时时间
    var apiService: T

    init {
        val httpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val builder = chain.request().newBuilder()
                    // 添加请求头header
                    if (getToken().isNotEmpty()) {
                        builder.header("userToken", getToken())
                    }
                    builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.108 Safari/537.36 2345Explorer/8.0.0.13547")
                    builder.addHeader("Cache-Control", "max-age=0")
                    builder.addHeader("Upgrade-Insecure-Requests", "1")
                    builder.addHeader("X-Requested-With", "XMLHttpRequest")
                    builder.addHeader("Cookie", "uuid=\"w:f2e0e469165542f8a3960f67cb354026\"; __tasessionId=4p6q77g6q1479458262778; csrftoken=7de2dd812d513441f85cf8272f015ce5; tt_webid=36385357187")
                    val build = builder.build()
                    chain.proceed(build)
                }
                .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                    if (message.contains("{")||message.contains("=")||message.contains("http")
                            ||message.contains("userToken")){
                        Logger.e("${message}")
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY))//设置打印得日志内容
                .connectTimeout(time_out, TimeUnit.SECONDS)
                .readTimeout(time_out, TimeUnit.SECONDS)
                .build()

        apiService = Retrofit.Builder()
                .baseUrl(URLConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(buildGson())) // 添加Gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Retrofit到RxJava的转换器
                .client(httpClient)
                .build()
                .create(getApiService())
    }

    abstract fun getApiService(): Class<T>
    abstract fun getToken(): String
    private fun buildGson(): Gson {
        return GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()
    }

    fun getService(): T {
        return apiService
    }
}