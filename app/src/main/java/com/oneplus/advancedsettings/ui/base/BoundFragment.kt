package com.oneplus.advancedsettings.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.kieronquinn.monetcompat.app.MonetFragment
import com.oneplus.advancedsettings.ui.utils.autoCleared


abstract class BoundFragment<T: ViewBinding>(private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> T): MonetFragment() {

    internal var binding by autoCleared<T>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val wrappedInflater = inflater.cloneInContext(requireContext())
        binding = inflate.invoke(wrappedInflater, container, false)
        return binding.root
    }

}