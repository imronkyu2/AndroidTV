package com.example.androidtv.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtv.data.Repository
import com.example.androidtv.data.model.CategoryModel
import com.example.androidtv.data.model.CouponModel
import com.example.androidtv.ui.util.StateUtil
import kotlinx.coroutines.launch

class AndroidTVViewModel(private val repository: Repository) : ViewModel() {
    private val _categories = MutableLiveData<StateUtil<List<CategoryModel>>>()
    private val _coupon = MutableLiveData<StateUtil<List<CouponModel>>>()

    val categories: LiveData<StateUtil<List<CategoryModel>>>
        get() = _categories

    val coupon: LiveData<StateUtil<List<CouponModel>>>
        get() = _coupon

    fun getCatagory() {
        viewModelScope.launch {
            _categories.postValue(StateUtil.Loading)
            try {
                val response = repository.getCategory()
                if (response.isSuccessful) {
                    val result = response.body()?.result
                    val model = result?.map { it.toModel() }?.toList()
                    _categories.postValue(StateUtil.Success("Success", model ?: emptyList()))
                } else {
                    _categories.postValue(StateUtil.Failed("Error"))
                }

                Log.e("TAG", "getCatagory: ", )

            } catch (e: Exception) {
                _categories.postValue(StateUtil.Failed(e.localizedMessage ?: "Error Catch"))
            }
        }

    }

    fun getCoupon() {
        viewModelScope.launch {
            _coupon.postValue(StateUtil.Loading)
            try {
                val response = repository.getCoupon()
                if (response.isSuccessful){
                    val result = response.body()?.result
                    val model = result?.map { it.toCouponModel()}?.toList()
                    _coupon.postValue(StateUtil.Success("Success", model?: emptyList()))
                }else{
                    _coupon.postValue(StateUtil.Failed("Error"))
                }
            }catch (e: Exception){
                _coupon.postValue(StateUtil.Failed(e.localizedMessage?: "Error Catch"))
            }
        }
    }

}