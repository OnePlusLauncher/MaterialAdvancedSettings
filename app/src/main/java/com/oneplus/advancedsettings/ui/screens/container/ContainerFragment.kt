package com.oneplus.advancedsettings.ui.screens.container

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.oneplus.advancedsettings.R
import com.oneplus.advancedsettings.databinding.FragmentContainerBinding
import com.oneplus.advancedsettings.ui.base.AutoExpandOnRotate
import com.oneplus.advancedsettings.ui.base.BoundFragment
import com.oneplus.advancedsettings.utils.extensions.hideKeyboard
import com.oneplus.advancedsettings.utils.extensions.isLandscape
import com.oneplus.advancedsettings.utils.extensions.navigateSafely
import org.koin.android.ext.android.inject
import kotlin.math.roundToInt

class ContainerFragment: BoundFragment<FragmentContainerBinding>(FragmentContainerBinding::inflate){

    private val navHostFragment by lazy {
        childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private val navController by lazy {
        navHostFragment.navController
    }

    private val navigation by inject<com.oneplus.advancedsettings.components.navigation.Navigation>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navHostFragment.isVisible = true
        setupBackground()
        setupInsets()
        setupToolbar()
    }


    private fun getTopFragment(): Fragment? {
        if(!navHostFragment.isAdded) return null
        return navHostFragment.childFragmentManager.fragments.firstOrNull()
    }

//    private fun onTopFragmentChanged(topFragment: Fragment){
//        val backIcon = if(topFragment is BackAvailable){
//            ContextCompat.getDrawable(requireContext(), R.drawable.ic_launcher_foreground)
//        } else null
//        if(topFragment is ProvidesOverflow){
////            setupMenu(topFragment)
//        }else{
////            setupMenu(null)
//        }
//        binding.toolbar.navigationIcon = backIcon
//        navController.currentDestination?.label?.let {
//            if(it.isBlank()) return@let
//            binding.collapsingToolbar.title = it
//            binding.toolbar.title = it
//        }
//    }

    private fun setupToolbar(){
        with(binding){
            collapsingToolbar.title = getString(R.string.app_name)
            appBar.setExpanded(!requireContext().isLandscape && getTopFragment() is AutoExpandOnRotate)
            toolbar.setNavigationOnClickListener {
                lifecycleScope.launchWhenResumed {
                    navigation.navigateBack()
                }
            }
            collapsingToolbar.setBackgroundColor(monet.getBackgroundColor(requireContext()))
            collapsingToolbar.setContentScrimColor(monet.getBackgroundColorSecondary(requireContext()) ?: monet.getBackgroundColor(requireContext()))
            collapsingToolbar.setExpandedTitleTypeface(ResourcesCompat.getFont(requireContext(), R.font.google_sans_text_medium))
            collapsingToolbar.setCollapsedTitleTypeface(ResourcesCompat.getFont(requireContext(), R.font.google_sans_text_medium))
            toolbar.overflowIcon?.setTint(ContextCompat.getColor(requireContext(), R.color.toolbar_overflow))
        }
    }
    private fun getToolbarHeight(): Int? {
        val typedValue = TypedValue()
        return if (requireContext().theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
            TypedValue.complexToDimensionPixelSize(typedValue.data, resources.displayMetrics)
        }else null
    }



    private fun handleNavigationEvent(navigationEvent: com.oneplus.advancedsettings.components.navigation.Navigation.NavigationEvent) {
        when (navigationEvent) {
            is com.oneplus.advancedsettings.components.navigation.Navigation.NavigationEvent.Directions -> navController.navigateSafely(navigationEvent.directions)
            is com.oneplus.advancedsettings.components.navigation.Navigation.NavigationEvent.Id -> navController.navigateSafely(navigationEvent.id)
            is com.oneplus.advancedsettings.components.navigation.Navigation.NavigationEvent.Back -> if(!navController.navigateUp()) activity?.finish()
            is com.oneplus.advancedsettings.components.navigation.Navigation.NavigationEvent.PopupTo -> navController.popBackStack(
                navigationEvent.id,
                navigationEvent.popInclusive
            )
        }
        if(activity?.isFinishing != true) {
            binding.appBar.setExpanded(true, true)
            activity?.hideKeyboard()
        }
    }
    private fun setupInsets(){
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val navigationInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            view.updatePadding(left = navigationInsets.left, right = navigationInsets.right)
            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.collapsingToolbar){ view, insets ->
            view.updateLayoutParams<AppBarLayout.LayoutParams> {
                val appBarHeight = view.context.resources.getDimension(R.dimen.app_bar_height)
                height = (appBarHeight + insets.getInsets(WindowInsetsCompat.Type.statusBars()).top).roundToInt()
            }
            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar){ view, insets ->
            getToolbarHeight()?.let {
                val statusInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
                val overflowPadding = resources.getDimension(R.dimen.padding_8)
                val topInset = statusInsets.top
                view.updateLayoutParams<CollapsingToolbarLayout.LayoutParams> {
                    height = it + topInset
                }
                view.updatePadding(left = statusInsets.left, top = topInset, right = statusInsets.right + overflowPadding.toInt())
            }
            insets
        }
    }

    private fun setupBackground(){
        binding.root.setBackgroundColor(monet.getBackgroundColor(requireContext()))
    }
    fun expandAppBar(){
        binding.appBar.setExpanded(true, true)
    }

}