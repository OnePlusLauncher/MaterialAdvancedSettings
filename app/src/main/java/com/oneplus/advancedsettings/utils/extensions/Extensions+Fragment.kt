package com.oneplus.advancedsettings.utils.extensions

import androidx.fragment.app.Fragment
import com.oneplus.advancedsettings.ui.screens.container.ContainerFragment

fun Fragment.expandAppBar(){
    (parentFragment as? ContainerFragment)?.expandAppBar()
}