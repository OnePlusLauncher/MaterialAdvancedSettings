package com.oneplus.advancedsettings.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openLink(url: String){
    try {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        })
    }catch (e: ActivityNotFoundException){
        //No browser
    }
}

object Links {
    const val LINK_XDA = "https://www.xda.com"
    const val LINK_TWITTER = "https://www.twittercom"
    const val LINK_DONATE = "https://www.baidu.com"
    const val LINK_GITHUB = "https://www.github.com"
}