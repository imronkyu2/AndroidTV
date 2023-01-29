package com.example.androidtv.ui.view

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import com.example.androidtv.data.model.CouponModel

class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {

    override fun onBindDescription(
        viewHolder: ViewHolder,
        item: Any
    ) {
        val couponModel = item as CouponModel

        viewHolder.title.text = couponModel.couponName
        viewHolder.subtitle.text = couponModel.couponBrandName
        viewHolder.body.text = couponModel.couponCategoryName
    }
}