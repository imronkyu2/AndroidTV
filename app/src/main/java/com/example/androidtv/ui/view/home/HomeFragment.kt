package com.example.androidtv.ui.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.androidtv.R
import com.example.androidtv.ui.inject.InjectAndroidTv
import com.example.androidtv.ui.util.StateUtil


class HomeFragment : Fragment() {
    private val viewModel by lazy { InjectAndroidTv.getViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        setObserver()
    }

    private fun setObserver() {
        viewModel.categories.observe(viewLifecycleOwner) {
            when (it) {
                is StateUtil.Loading -> {
                    showToast("Loading")
                    Log.e("TAG", "setObserver: Loading")
                }

                is StateUtil.Success -> {
                    showToast(it.message)
                    Log.e("TAG", "setObserver: SUCCESS ${it.message}")

                }

                is StateUtil.Failed -> {
                    showToast(it.message)
                    Log.e("TAG", "setObserver: FAILED ${it.message}")

                }
            }
        }

        viewModel.coupon.observe(viewLifecycleOwner) {
            when (it) {
                is StateUtil.Loading -> {
                    showToast("Loading")
                    Log.e("TAG", "setObserver: Loading")
                }

                is StateUtil.Success -> {
                    showToast(it.message)
                    Log.e("TAG", "setObserver: SUCCESS ${it.message}")

                }

                is StateUtil.Failed -> {
                    showToast(it.message)
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
        viewModel.getCoupon()
    }

}