package com.feylabs.sumbangsih.data.source.remote.web

import com.feylabs.sumbangsih.data.source.remote.response.*
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Response

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*


interface CommonApiClient {

    @POST("ktp/{nik}/uploadVerification")
    @JvmSuppressWildcards
    fun editDynamicInfo(
        @Path("nik") nik: String,
        @Body dynamicFormData: Map<String, Any>
    ): Call<CommonResponse>

    @POST("pengajuan/upload")
    @JvmSuppressWildcards
    fun uploadPengajuan(
        @Body dynamicFormData: Map<String, Any>
    ): Call<CommonResponse>

    @POST("komplain/upload")
    @JvmSuppressWildcards
    fun uploadKomplain(
        @Body dynamicFormData: Map<String, Any>
    ): Call<CommonResponse>

    @POST("fix-data/upload")
    @JvmSuppressWildcards
    fun uploadFixData(
        @Body dynamicFormData: Map<String, Any>
    ): Call<CommonResponse>

    @POST("pengajuan/activeEvent")
    fun getActiveEvent(): Call<CheckActiveEventRes>

    @GET("pengajuan/self-check")
    fun selfCheckPengajuan(
        @Query("id") id: String,
    ): Call<SelfCheckPengajuanRes>

    @GET("pengajuan/history")
    fun getHistoryResponse(
        @Query("user_id") id: String,
    ): Call<GetHistoryResponse>

}