package com.villa.vpexample.http

import com.exmple.corelib.http.entity.BaseBean
import com.exmple.corelib.http.entity.ListBean
import com.villa.vpexample.data.ArticleData
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * FileName: com.beijing.zhagen.meiqi.http.api.MainApi.java
 * Author: mouxuefei
 * date: 2018/3/20
 * version: V1.0
 * desc:
 */
interface MainApi {


    /**
     * 统计活跃度
     */
    @GET("article/list/0/json")
    fun getArticle(): Observable<BaseBean<ListBean<ArticleData>>>


}
