package com.growgenie.kidsyCore.model.screenState.screen.onboarding

import com.growgenie.kidsyCore.model.screenState.ScreenName
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.UserAction

data class OnboardingProcessingScreenState(val state: State = State.TEXT1): ScreenState {
    override val screenName: ScreenName = ScreenName.ONBOARDING_PROCESSING

    enum class State { TEXT1, TEXT2, TEXT3, DONE }

    enum class Action: UserAction { CREATE_AN_ACCOUNT, MOVE_TO_NEXT_STEP, LOGIN_IN }

    val prefixText = "Creating program based\non"
    val text1 = "your goals"
    val text2 = "being single mom"
    val text3 = "kids sleep routine"
    val doneText = "Your program is ready"
    val createAnAccount = "Create an account"

    val currentText: String
        get() {
            return when(state) {
                State.TEXT1 -> text1
                State.TEXT2 -> text2
                State.TEXT3 -> text3
                State.DONE -> doneText
            }
        }

    fun nextScreen(): OnboardingProcessingScreenState {
        return when(state) {
            State.TEXT1 -> copy(state = State.TEXT2)
            State.TEXT2 -> copy(state = State.TEXT3)
            State.TEXT3 -> copy(state = State.DONE)
            State.DONE ->  throw IllegalArgumentException("After done there is no more screen to show")
        }
    }
}