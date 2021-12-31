package com.example.testapp.common.util

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Hide view unless condition is true
 **/
@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible: Boolean?) {
    visible?.let {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}