package com.exmple.baseprojectmvp.http

import com.exmple.corelib.http.entity.BaseBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
    @FormUrlEncoded
    @POST("v1.phone/join")
    fun loginApp(@Field("uuid") uuid: String): Observable<BaseBean>


}
