package com.oneplus.advancedsettings.components.settings

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.SharedPreferences
import com.oneplus.advancedsettings.ui.utils.SharedPref
import kotlin.properties.ReadWriteProperty

private lateinit var APPCTX: Context
class OPSharedPreferences {
    object Setting {
        var enabled by SharedPref(APPCTX, "enabled", false)
        var Introduce by SharedPref(APPCTX, "IntroduceS", true)
    }
}