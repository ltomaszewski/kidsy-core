package com.growgenie.kidsyCore.model.screenState.screen.onboarding

import com.growgenie.kidsyCore.model.screenState.ScreenName
import com.growgenie.kidsyCore.model.screenState.ScreenState

data class OnboardingSuccessState(val state: State = State.NORMAL): ScreenState {
    override val screenName: ScreenName = ScreenName.ONBOARDING_SUCCESS
    enum class State {
        NORMAL
    }

    val backgroundColor: String = "#FFE9FE"
    val imageName: String = "check"
    val headline: String = "Your program is ready."
    val createAnAccount: String = "Create an account"
    val alreadyHaveAnAccount: String = "Already have an account? Log in"
    val alreadyHaveAnAccountBolt: String = "Log in"
}