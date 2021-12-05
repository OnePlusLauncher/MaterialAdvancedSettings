package com.oneplus.advancedsettings.components.navigation

import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class Navigation {

    abstract val navigationBus: Flow<com.oneplus.advancedsettings.components.navigation.Navigation.NavigationEvent>

    abstract suspend fun navigate(navDirections: NavDirections)
    abstract suspend fun navigate(@IdRes id: Int)
    abstract suspend fun navigateUpTo(@IdRes id: Int, popInclusive: Boolean = false)
    abstract suspend fun navigateBack()

    sealed class NavigationEvent {
        data class Directions(val directions: NavDirections): com.oneplus.advancedsettings.components.navigation.Navigation.NavigationEvent()
        data class Id(@IdRes val id: Int): com.oneplus.advancedsettings.components.navigation.Navigation.NavigationEvent()
        data class PopupTo(@IdRes val id: Int, val popInclusive: Boolean): com.oneplus.advancedsettings.components.navigation.Navigation.NavigationEvent()
        object Back: com.oneplus.advancedsettings.components.navigation.Navigation.NavigationEvent()
    }

}

class NavigationImpl: com.oneplus.advancedsettings.components.navigation.Navigation() {

    private val _navigationBus = MutableSharedFlow<com.oneplus.advancedsettings.components.navigation.Navigation.NavigationEvent>()
    override val navigationBus = _navigationBus.asSharedFlow()

    override suspend fun navigate(id: Int) {
        _navigationBus.emit(
            com.oneplus.advancedsettings.components.navigation.Navigation.NavigationEvent.Id(
                id
            )
        )
    }

    override suspend fun navigate(navDirections: NavDirections) {
        _navigationBus.emit(
            com.oneplus.advancedsettings.components.navigation.Navigation.NavigationEvent.Directions(
                navDirections
            )
        )
    }

    override suspend fun navigateBack() {
        _navigationBus.emit(com.oneplus.advancedsettings.components.navigation.Navigation.NavigationEvent.Back)
    }

    override suspend fun navigateUpTo(id: Int, popInclusive: Boolean) {
        _navigationBus.emit(
            com.oneplus.advancedsettings.components.navigation.Navigation.NavigationEvent.PopupTo(
                id,
                popInclusive
            )
        )
    }

}