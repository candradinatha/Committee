package com.example.candradinatha.committee.api

import com.example.candradinatha.committee.model.*
import io.reactivex.Flowable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email:String, @Field("password") password:String): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(@Field("name") name:String, @Field("email") email: String, @Field("password") password: String): Call<RegisterResponse>

    @GET("details")
    fun getDetail(): Call<DetailUserResponse>

    @GET("Kegiatan")
    fun getCommittee(@Query("sts") sts: String): Flowable<CommitteeResponse>

//    add committee
    @Multipart
    @POST("Kegiatan")
    fun addCommittee(@Part("role") role: RequestBody,
                     @Part("nama") nama: RequestBody,
                     @Part("tgl_kegiatan") tgl_kegiatan: RequestBody,
                     @Part("tgl_rapat_perdana") tgl_rapat_perdana: RequestBody,
                     @Part("des") des: RequestBody,
                     @Part foto: MultipartBody.Part): Call<AddCommitteeResponse>

//    @FormUrlEncoded
//    @POST("Kegiatan")
//    fun addCommittee(@Field("role") role: String,
//                    @Field("nama") nama: String,
//                    @Field("tgl_kegiatan") tgl_kegiatan: String,
//                    @Field("tgl_rapat_perdana") tgl_rapat_perdana: String,
//                    @Field("des") des: String): Call<AddCommitteeResponse>


//    update
    @FormUrlEncoded
    @POST("Kegiatan")
    fun updateCommittee(@Field("role") role: String,
                        @Field("id") id: String,
                        @Field("nama") nama: String,
                        @Field("tgl_kegiatan") tgl_kegiatan: String,
                        @Field("tgl_rapat_perdana") tgl_rapat_perdana: String,
                        @Field("des") des: String,
                        @Field("foto") foto: String): Call<AddCommitteeResponse>
//    update
    @FormUrlEncoded
    @POST("Kegiatan")
    fun updateStatus(
            @Field("role") role: String,
            @Field("id") id: String): Call<ActivateCommitteeResponse>



//    add sie kegiatan
    @FormUrlEncoded
    @POST("SieKegiatan")
    fun addSie(@Field("id_kegiatan") id: String,
               @Field("sie") sie: String,
               @Field("job") job: String,
               @Field("kuota") kuota: String,
               @Field("koor") koor: String,
               @Field("line") line: String): Call<AddSieResponse>
}

