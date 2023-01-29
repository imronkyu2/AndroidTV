package com.example.androidtv.ui.view

import android.os.Build
import android.text.Html
import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import com.example.androidtv.data.model.CouponModel

class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {

    override fun onBindDescription(
        viewHolder: ViewHolder,
        item: Any
    ) {
        val couponModel = item as CouponModel
        var str:String = couponModel.couponTnc
        str = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(str).toString()
        }
        viewHolder.title.text = couponModel.couponName
        viewHolder.body.text = str
    }
}