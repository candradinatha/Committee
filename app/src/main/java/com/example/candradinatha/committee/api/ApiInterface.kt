package com.example.candradinatha.committee.api

import com.example.candradinatha.committee.model.*
import io.reactivex.Flowable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @FormUrlEncoded
    @POST("Login")
    fun login(@Field("username") username:String, @Field("password") password:String): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(@Field("name") name:String, @Field("email") email: String, @Field("password") password: String): Call<RegisterResponse>

    @GET("details")
    fun getDetail(): Call<DetailUserResponse>

//    get committee /////////////////////////////////////////
    @GET("Kegiatan")
    fun getCommittee(@Query("sts") sts: String): Flowable<CommitteeResponse>

    @GET("Kegiatan")
    fun getCommitteeId(@Query("id") idKegiatan: String): Flowable<CommitteeResponse>

//    add committee
    @Multipart
    @POST("Kegiatan")
    fun addCommittee(@Part("role") role: RequestBody,
                     @Part("nama") nama: RequestBody,
                     @Part("tgl_kegiatan") tgl_kegiatan: RequestBody,
                     @Part("tgl_rapat_perdana") tgl_rapat_perdana: RequestBody,
                     @Part("des") des: RequestBody,
                     @Part foto: MultipartBody.Part): Call<AddCommitteeResponse>

//    update committee
    @Multipart
    @POST("Kegiatan")
    fun updateCommittee(@Part("role") role: RequestBody,
                        @Part("id") idKegiatan: RequestBody,
                        @Part("nama") nama: RequestBody,
                        @Part("tgl_kegiatan") tgl_kegiatan: RequestBody,
                        @Part("tgl_rapat_perdana") tgl_rapat_perdana: RequestBody,
                        @Part("des") des: RequestBody,
                        @Part foto: MultipartBody.Part): Call<AddCommitteeResponse>

//    update committee status
    @FormUrlEncoded
    @POST("Kegiatan")
    fun updateStatus(
            @Field("role") role: String,
            @Field("id") id: String): Call<ActivateCommitteeResponse>

//    get sie ///////////////////////////////////////////////////
    @GET("SieKegiatan")
    fun getSie(@Query("kegiatan") idKegiatan: String): Flowable<SieResponse>

//    add sie
    @FormUrlEncoded
    @POST("SieKegiatan")
    fun addSie(@Field("role") role: String,
               @Field("id_kegiatan") id: String,
               @Field("sie") sie: String,
               @Field("job") job: String,
               @Field("kuota") kuota: String,
               @Field("koor") koor: String,
               @Field("line") line: String): Call<AddSieResponse>

//    update sie
    @FormUrlEncoded
    @PUT("SieKegiatan")
    fun updateSie(@Field("id") idSie: String,
                  @Field("id_kegiatan") idKegiatan: String,
                  @Field("sie") sie: String,
                  @Field("job") job: String,
                  @Field("kuota") kuota: String,
                  @Field("koor") koor: String,
                  @Field("line") line: String): Call<AddSieResponse>

//    delete sie
    @FormUrlEncoded
    @POST("SieKegiatan")
    fun deleteSie(@Field("role") role: String, @Field("id") id: String)  : Call<DeleteSieResponse>

    //get member ///////////////////////////////////////////////////
    @GET("Member")
    fun getMemberId(@Query("id") idMember: String): Call<MemberResponse>
}



