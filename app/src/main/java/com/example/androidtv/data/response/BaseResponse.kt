package com.example.androidtv.data.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(

	@field:SerializedName("result")
	val result: List<T>? = null,

	@field:SerializedName("message")
	val message: List<String>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("statusCode")
	val statusCode: String? = null,

	@field:SerializedName("option")
	val option: String? = null
)