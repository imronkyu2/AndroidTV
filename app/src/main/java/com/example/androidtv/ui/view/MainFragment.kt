package com.example.androidtv.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.androidtv.R
import com.example.androidtv.data.model.CategoryModel
import com.example.androidtv.data.model.CouponModel
import com.example.androidtv.ui.inject.InjectAndroidTv
import com.example.androidtv.ui.util.StateUtil
import com.google.gson.Gson
import java.util.*

/**
 * Loads a grid of cards with movies to browse.
 */
class MainFragment : BrowseSupportFragment() {
    private val viewModel by lazy { InjectAndroidTv.getViewModel() }
    private val mHandler = Handler(Looper.myLooper()!!)
    private lateinit var mBackgroundManager: BackgroundManager
    private var mDefaultBackground: Drawable? = null
    private lateinit var mMetrics: DisplayMetrics
    private var mBackgroundTimer: Timer? = null
    private var mBackgroundUri: String? = null
    private val categoryList: ArrayList<CategoryModel> = arrayListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e(TAG, "CEKSTATE on activity created")
        getData()
        dataObserver()

        prepareBackgroundManager()
        setupUIElements()
        setupEventListeners()


    }

    private fun dataObserver() {
        categoriesObserve()
        couponObserver()
    }

    private fun couponObserver() {
        viewModel.coupon.observe(viewLifecycleOwner) {
            when (it) {
                is StateUtil.Loading -> {
                    Log.e("TAG", "setObserver: Loading")
                }

                is StateUtil.Success -> {
                    showToast(it.message)
                    Log.e("TAG", "setObserver coupon: SUCCESS ${it.message}")
                    setData(it.data)
                }

                is StateUtil.Failed -> {
                    Log.e("TAG", "setObserver: FAILED ${it.message}")

                }
            }
        }
    }

    private fun setData(data: List<CouponModel>) {
        Log.d(TAG, "setData  Category: ${categoryList.size}")
        Log.d(TAG, "setData Coupon: ${data.size}")
        loadRowData(data, categoryList)

    }

    private fun loadRowData(data: List<CouponModel>, categoryList: ArrayList<CategoryModel>) {
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val cardPresenter = CardPresenter()

        for (i in 0 until categoryList.size) {
            val listRowAdapter = ArrayObjectAdapter(cardPresenter)
            val listMovie =
                data.filter { item -> item.couponCategoryId == categoryList[i].categoryId }

            listMovie.forEach {
                listRowAdapter.add(it)
            }

            val header = HeaderItem(i.toLong(), categoryList[i].categoryName)
            rowsAdapter.add(ListRow(header, listRowAdapter))

        }
        adapter = rowsAdapter
    }

    private fun categoriesObserve() {
        viewModel.categories.observe(viewLifecycleOwner) {
            when (it) {
                is StateUtil.Loading -> {
                    Log.e("TAG", "setObserver: Loading")
                }

                is StateUtil.Success -> {
                    Log.e("TAG", "setObserver category: SUCCESS ${it.message}")
                    categoryList.clear()
                    categoryList.addAll(it.data)
                    viewModel.getCoupon()

                }

                is StateUtil.Failed -> {
                    Log.e("TAG", "setObserver: FAILED ${it.message}")
                }
            }
        }
    }

    private fun showToast(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()

    }

    private fun getData() {
        viewModel.getCatagory()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: " + mBackgroundTimer?.toString())
        mBackgroundTimer?.cancel()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager.attach(activity!!.window)
        mDefaultBackground = ContextCompat.getDrawable(activity!!, R.drawable.default_background)
        mMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(mMetrics)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun setupUIElements() {
        title = getString(R.string.browse_title)
        // over title
        headersState = BrowseSupportFragment.HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true

        // set fastLane (or headers) background color
        brandColor = ContextCompat.getColor(activity!!, R.color.background_gradient_start)
        // set search icon color
        searchAffordanceColor = ContextCompat.getColor(activity!!, R.color.search_opaque)
    }


    private fun setupEventListeners() {
        setOnSearchClickedListener {
            Toast.makeText(requireActivity(), "Implement your own in-app search", Toast.LENGTH_LONG)
                .show()
        }

        onItemViewClickedListener = ItemViewClickedListener()
        onItemViewSelectedListener = ItemViewSelectedListener()
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder,
            item: Any,
            rowViewHolder: RowPresenter.ViewHolder,
            row: Row
        ) {

            if (item is CouponModel) {
                val stringContent = Gson().toJson(item)
                Log.d(TAG, "Item: " + item.toString())
                val intent = Intent(activity!!, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.MOVIE, stringContent)

                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity!!,
                    (itemViewHolder.view as ImageCardView).mainImageView,
                    DetailsActivity.SHARED_ELEMENT_NAME
                )
                    .toBundle()
                startActivity(intent, bundle)
            } else if (item is String) {
                if (item.contains(getString(R.string.error_fragment))) {
                    val intent = Intent(activity!!, BrowseErrorActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(activity!!, item, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?, item: Any?,
            rowViewHolder: RowPresenter.ViewHolder, row: Row
        ) {
            if (item is CouponModel) {
                mBackgroundUri = item.couponBrandLogo
                startBackgroundTimer()
            }
        }
    }

    private fun updateBackground(uri: String?) {
        val width = mMetrics.widthPixels
        val height = mMetrics.heightPixels
        Glide.with(requireActivity())
            .load(uri)
            .centerCrop()
            .error(mDefaultBackground)
            .into<SimpleTarget<Drawable>>(
                object : SimpleTarget<Drawable>(width, height) {
                    override fun onResourceReady(
                        drawable: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        mBackgroundManager.drawable = drawable
                    }
                })
        mBackgroundTimer?.cancel()
    }

    private fun startBackgroundTimer() {
        mBackgroundTimer?.cancel()
        mBackgroundTimer = Timer()
        mBackgroundTimer?.schedule(UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY.toLong())
    }

    private inner class UpdateBackgroundTask : TimerTask() {

        override fun run() {
            mHandler.post { updateBackground(mBackgroundUri) }
        }
    }

    companion object {
        private val TAG = "MainFragment"

        private val BACKGROUND_UPDATE_DELAY = 300
    }
}