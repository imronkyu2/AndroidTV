package com.example.androidtv.data.response

import com.example.androidtv.data.model.CategoryModel
import com.google.gson.annotations.SerializedName

data class CategoryResponse(

	@field:SerializedName("categoryName")
	val categoryName: String? = null,

	@field:SerializedName("categoryId")
	val categoryId: String? = null
){
	fun toModel():CategoryModel = CategoryModel(
		categoryName = categoryName?: "",
		categoryId = categoryId?:""
	)
}
