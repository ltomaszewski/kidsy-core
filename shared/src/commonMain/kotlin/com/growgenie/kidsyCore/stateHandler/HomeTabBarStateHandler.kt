package com.growgenie.kidsyCore.stateHandler

import com.growgenie.kidsyCore.UserSession.UserSession
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.screen.home.HomeTabBarScreenState

class HomeTabBarStateHandler(private val userSession: UserSession) :
    StateHandler<HomeTabBarScreenState, HomeTabBarScreenState.Action> {
    override fun handle(
        state: HomeTabBarScreenState,
        action: HomeTabBarScreenState.Action
    ): ScreenState {
        return when (action.type) {
            HomeTabBarScreenState.ActionType.OPEN_TAB -> {
                state.copy(state = action.newTabBarState)
            }
        }
    }
}