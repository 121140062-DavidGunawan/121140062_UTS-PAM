package com.example.projectuts.network

import com.example.projectuts.model.ResponseUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface serviceAPI {
    @GET("api/users")
    fun getListUsers(@Query("page") page: String): Call<ResponseUser>

    @GET("api/users/{id}")
    fun getUser(@Path("id") id: String): Call<ResponseUser>

    @FormUrlEncoded
    @POST("api/users")
    fun createUser(
        @Field("name") name: String,
        @Field("job") job: String
    ): Call<ResponseUser>

    @Multipart
    @PUT("api/uploadfile")
    fun updateUser(
        @Part("file") file: MultipartBody.Part,
        @PartMap data: Map<String, RequestBody>
    ): Call<ResponseUser>

}