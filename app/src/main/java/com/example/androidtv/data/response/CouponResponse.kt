package com.example.androidtv.data.response

import com.example.androidtv.data.model.CouponModel
import com.google.gson.annotations.SerializedName

data class CouponResponse(

	@field:SerializedName("couponName")
	val couponName: String? = null,

	@field:SerializedName("couponTnc")
	val couponTnc: String? = null,

	@field:SerializedName("couponCreatedAt")
	val couponCreatedAt: String? = null,

	@field:SerializedName("couponEndDate")
	val couponEndDate: String? = null,

	@field:SerializedName("couponBenefitValue")
	val couponBenefitValue: String? = null,

	@field:SerializedName("couponId")
	val couponId: String? = null,

	@field:SerializedName("couponCategoryName")
	val couponCategoryName: String? = null,

	@field:SerializedName("couponBenefitType")
	val couponBenefitType: String? = null,

	@field:SerializedName("couponUpdatedAt")
	val couponUpdatedAt: String? = null,

	@field:SerializedName("couponBenefitUnit")
	val couponBenefitUnit: Any? = null,

	@field:SerializedName("couponBrandLogo")
	val couponBrandLogo: String? = null,

	@field:SerializedName("couponBrandName")
	val couponBrandName: String? = null,

	@field:SerializedName("couponStatus")
	val couponStatus: String? = null,

	@field:SerializedName("couponQuota")
	val couponQuota: String? = null,

	@field:SerializedName("couponStartDate")
	val couponStartDate: String? = null,

	@field:SerializedName("couponCategoryId")
	val couponCategoryId: String? = null
){
	fun toCouponModel(): CouponModel = CouponModel(
		couponName = couponName?: "",
		couponTnc = couponTnc?: "",
		couponCreatedAt = couponCreatedAt?:"",
		couponEndDate = couponEndDate?:"",
		couponBenefitValue = couponBenefitValue?:"",
		couponId = couponId?:"",
		couponCategoryName = couponCategoryName?:"",
		couponBenefitType = couponBenefitType?:"",
		couponUpdatedAt = couponUpdatedAt?:"",
		couponBenefitUnit = couponBenefitUnit?:"",
		couponBrandLogo = couponBrandLogo?:"",
		couponBrandName = couponBrandName?:"",
		couponStatus = couponStatus?:"",
		couponQuota = couponQuota?:"",
		couponStartDate = couponStartDate?:"",
		couponCategoryId = couponCategoryId?:""
	)
}