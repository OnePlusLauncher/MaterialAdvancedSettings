package com.oneplus.advancedsettings.ui.activities

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.AttributeSet
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.View
import androidx.appcompat.view.menu.ListMenuItemView
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.kieronquinn.monetcompat.app.MonetCompatActivity
import com.kieronquinn.monetcompat.extensions.views.applyMonetRecursively
import com.oneplus.advancedsettings.databinding.ActivityMainBinding
import com.oneplus.advancedsettings.utils.extensions.isDarkTheme
import com.oneplus.advancedsettings.R

class MainActivity : MonetCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SetLightStatusBar()
        binding = ActivityMainBinding.inflate(layoutInflater)
        WindowCompat.setDecorFitsSystemWindows(window, false)
//        setUseLightStatusNav(false)
        lifecycleScope.launchWhenResumed {
            monet.awaitMonetReady()
            setContentView(R.layout.activity_main)
            window.setBackgroundDrawable(ColorDrawable(monet.getBackgroundColor(this@MainActivity)))
//            val lightStatusNav = resources.getBoolean(R.bool.lightStatusNav)
//            setUseLightStatusNav(lightStatusNav)

        }

    }

    private fun SetLightStatusBar() {
        val systemUiVisibility =this.window.decorView.systemUiVisibility
        if(!this.isDarkTheme){
            this.window.decorView.systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }else{

        }
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        if (applyBackgroundColorToMenu && name == ListMenuItemView::class.java.name) {
            //Toolbar dropdown menu list item
            (parent?.parent as? View)?.run {
                post {
                    applyMonetRecursively()
                }
            }
        }
        return super.onCreateView(parent, name, context, attrs)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
//    private fun setUseLightStatusNav(enabled: Boolean){
//        WindowCompat.getInsetsController(window, window.decorView)?.run {
//            isAppearanceLightNavigationBars = enabled
//            isAppearanceLightStatusBars = enabled
//        }
//    }

}