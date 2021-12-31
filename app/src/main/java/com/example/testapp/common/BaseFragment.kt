package com.example.testapp.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.testapp.common.viewmodel.base.ViewModelFactory

abstract class BaseFragment : Fragment() {

    inline fun <reified T : ViewModel> ViewModelFactory<T>.get(): T =
        ViewModelProviders.of(this@BaseFragment, this)[T::class.java]

}