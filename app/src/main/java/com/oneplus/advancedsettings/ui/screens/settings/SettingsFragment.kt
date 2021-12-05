package com.oneplus.advancedsettings.ui.screens.settings

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.text.style.TypefaceSpan
import android.view.*
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.oneplus.advancedsettings.databinding.FragmentSettingsBinding
import com.oneplus.advancedsettings.model.settings.SettingsItem
import com.oneplus.advancedsettings.ui.base.AutoExpandOnRotate
import com.oneplus.advancedsettings.ui.base.ProvidesOverflow
import com.kieronquinn.monetcompat.extensions.views.applyMonetRecursively
import com.oneplus.advancedsettings.R
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseSettingsFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate), AutoExpandOnRotate, ProvidesOverflow {

    private val viewModel by viewModel<SettingsViewModel>()
    override val settingsItems: MutableList<SettingsItem> by lazy {
        listOf(
            SettingsItem.Header(getString(R.string.item_header_options)),
            SettingsItem.SwitchSetting(
                R.mipmap.ic_launcher_round,
                getString(R.string.test),
                getString(R.string.item_test_content),
                true
            ),
            SettingsItem.Header(getString(R.string.item_header_about)),
            SettingsItem.AboutSetting(
               R.mipmap.ic_launcher_round,
                getString(R.string.item_about_title),
                getString(R.string.item_about_content),
            )
        ).toMutableList()
    }

    private val adapter by lazy {
        SettingsAdapter(requireContext(), settingsItems)
    }

    private val googleSans by lazy {
        ResourcesCompat.getFont(requireContext(), R.font.google_sans_text)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        view?.applyMonetRecursively()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMainSwitch()
        setupRecyclerView(binding.recyclerView, adapter)
        //setupSnackbarPadding(binding.recyclerView)
        setupIntrolduceS()
    }

    private fun setupMainSwitch() {
        binding.switchMain.run {
            mainSwitchSwitch.typeface = ResourcesCompat.getFont(requireContext(), R.font.google_sans_text_medium)
            binding.switchMain.mainSwitchSwitch.text = getMainSwitchLabel(null)
        }
        lifecycleScope.launch {
        }
    }


    private fun setupIntrolduceS(){
        lifecycleScope.launchWhenResumed {
        }
    }


    private fun getMainSwitchLabel(@StringRes subtitle: Int?): CharSequence {
        return if(subtitle == null){
            getString(R.string.item_switch_main_title)
        }else{
            val sizeSpan = RelativeSizeSpan(0.75f)
            val fontSpan = TypefaceSpan(googleSans ?: Typeface.DEFAULT)
            val secondLine = SpannableString(getString(subtitle)).apply {
                setSpan(sizeSpan, 0, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                setSpan(fontSpan, 0, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            }
            SpannableStringBuilder().apply {
                appendLine(getString(R.string.item_switch_main_title))
                append(secondLine)
            }
        }
    }


    override fun inflateMenu(menuInflater: MenuInflater, menu: Menu) {
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        TODO("Not yet implemented")
    }


}