package com.example.androidtv.data

import com.example.androidtv.data.response.BaseResponse
import com.example.androidtv.data.response.CategoryResponse
import com.example.androidtv.data.response.CouponResponse
import retrofit2.Response

class Repository (private val  service: ApiService){
    suspend fun getCategory(): Response<BaseResponse<CategoryResponse>>{
        return service.getAllCategory()
    }

    suspend fun getCoupon(): Response<BaseResponse<CouponResponse>>{
        return service.getAllCoupon()
    }
}