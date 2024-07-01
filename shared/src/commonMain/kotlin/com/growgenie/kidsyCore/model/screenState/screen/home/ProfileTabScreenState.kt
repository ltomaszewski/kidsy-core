package com.growgenie.kidsyCore.model.screenState.screen.home

import com.growgenie.kidsyCore.model.screenState.ScreenName
import com.growgenie.kidsyCore.model.screenState.ScreenState

class ProfileTabScreenState : ScreenState {
    override val screenName: ScreenName = ScreenName.PROFILE

    val headlineText: String = "Profile"
}