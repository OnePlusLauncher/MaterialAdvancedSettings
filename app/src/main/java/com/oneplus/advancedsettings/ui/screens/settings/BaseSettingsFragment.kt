package  com.oneplus.advancedsettings.ui.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.oneplus.advancedsettings.components.settings.OPSharedPreferences
import com.oneplus.advancedsettings.model.settings.SettingsItem
import com.oneplus.advancedsettings.ui.base.BoundFragment
import com.oneplus.advancedsettings.ui.utils.TransitionUtils
import org.koin.android.ext.android.inject

abstract class BaseSettingsFragment<T: ViewBinding>(inflate: (LayoutInflater, ViewGroup?, Boolean) -> T): BoundFragment<T>(inflate){

    internal abstract val settingsItems: MutableList<SettingsItem>
    internal val settings by inject<OPSharedPreferences>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = TransitionUtils.getMaterialSharedAxis(requireContext(), true)
        enterTransition = TransitionUtils.getMaterialSharedAxis(requireContext(), true)
        returnTransition = TransitionUtils.getMaterialSharedAxis(requireContext(), false)
        reenterTransition = TransitionUtils.getMaterialSharedAxis(requireContext(), false)
    }

    internal fun setupRecyclerView(recyclerView: RecyclerView, settingsAdapter: SettingsAdapter) {
        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = settingsAdapter
        }
        ViewCompat.setOnApplyWindowInsetsListener(recyclerView){ view, insets ->
            val bottomInset = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            view.updatePadding(bottom = bottomInset)
            insets
        }
    }


}