package com.growgenie.kidsyCore.model.screenState.screen

import com.growgenie.kidsyCore.model.screenState.ScreenName
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.UserAction

data class IntroScreenState(val state: State = State.START) : ScreenState {
    override val screenName: ScreenName = ScreenName.INTRO

    enum class State {
        START, LOADING
    }

    enum class Action: UserAction {
        START_FOR_FREE, LOG_IN
    }

    val backgroundColor: String = "#FFE9FE"
    val imageName: String = "kido"
    val headline: String = "Personalized parent program that adapts to your kid."
    val headlineBolt: String = "your kid."
    val startForFree: String = "Start for free"
    val alreadyHaveAnAccount: String = "Already have an account? Log in"
    val alreadyHaveAnAccountBolt: String = "Log in"
}