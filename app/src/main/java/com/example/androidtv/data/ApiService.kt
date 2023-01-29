package com.example.androidtv.data

import com.example.androidtv.data.response.BaseResponse
import com.example.androidtv.data.response.CategoryResponse
import com.example.androidtv.data.response.CouponResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("getAllCategory")
    suspend fun getAllCategory(): Response<BaseResponse<CategoryResponse>>

    @GET("getCoupon")
    suspend fun getAllCoupon(): Response<BaseResponse<CouponResponse>>
}