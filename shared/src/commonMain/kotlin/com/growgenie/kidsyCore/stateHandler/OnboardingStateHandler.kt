package com.growgenie.kidsyCore.stateHandler

import com.growgenie.kidsyCore.UserSession.UserSession
import com.growgenie.kidsyCore.UserSession.UserSessionOption
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.screen.onboarding.OnboardingScreenState
import com.growgenie.kidsyCore.model.screenState.screen.onboarding.OnboardingSuccessScreenState

class OnboardingStateHandler(private val userSession: UserSession) :
    StateHandler<OnboardingScreenState, OnboardingScreenState.Action> {

    init {
        println("OnboardingStateHandler initialized")
    }

    override fun handle(
        state: OnboardingScreenState,
        action: OnboardingScreenState.Action
    ): ScreenState {
        println("OnboardingStateHandler Handling action: ${action}")
        return when (action.type) {
            OnboardingScreenState.ActionType.SUBMIT -> {
                println("OnboardingStateHandler Submitting Onboarding Data...")
                val nextScreenState = state.nextScreen()
                if (nextScreenState.state != OnboardingScreenState.State.DONE) {
                    nextScreenState
                } else {
                    OnboardingSuccessScreenState()
                }
            }

            OnboardingScreenState.ActionType.SELECT -> {
                println("OnboardingStateHandler Selecting options...")
                if (action.option is Int) {

                    println("OnboardingStateHandler Update user session SELECT...")
                    userSession.addOrUpdateOnboardingData(
                        state.currentScreenModel.id,
                        UserSessionOption().apply {
                            this.jsonId = state.currentScreenModel.id
                            this.text = action.option.toString()
                        }
                    )
                    state.updateWithSelectedOption(action.option)
                } else {
                    throw IllegalArgumentException("Unknown option selected")
                }
            }

            OnboardingScreenState.ActionType.TEXT_INPUT -> {
                println("OnboardingStateHandler TEXT_INPUT value...")
                println("OnboardingStateHandler Update user session TEXT_INPUT...")
                userSession.addOrUpdateOnboardingData(
                    state.currentScreenModel.id,
                    UserSessionOption().apply {
                        this.jsonId = state.currentScreenModel.id
                        this.text = action.text ?: ""
                    }
                )

                val nextScreenState = state.nextScreen()
                if (nextScreenState.state != OnboardingScreenState.State.DONE) {
                    nextScreenState
                } else {
                    OnboardingSuccessScreenState()
                }
            }
        }
    }
}