package com.growgenie.kidsyCore

import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.UserAction
import com.growgenie.kidsyCore.model.screenState.screen.*
import com.growgenie.kidsyCore.model.screenState.screen.onboarding.*
import com.growgenie.kidsyCore.utils.wrap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class KidsyStateManager {
    private val _screenState = MutableStateFlow<ScreenState>(IntroScreenState())
    val screenState = _screenState.asStateFlow().wrap()

    fun executeAction(action: UserAction) {
        when (screenState.value) {
            is IntroScreenState -> handleIntroAction(screenState.value, action as IntroScreenState.Action)
            is OnboardingScreenState -> handleOnboardingAction(screenState.value, action as OnboardingScreenState.Action)
            is ProcessingScreenState -> handleProcessingAction(screenState.value, action as ProcessingScreenState.Action)
            is CreateAnAccountScreenState -> handleCreateAccountAction(screenState.value, action as CreateAnAccountScreenState.Action)
            else -> throw IllegalArgumentException("Unknown screen type")
        }
    }

    private fun handleIntroAction(screenState: IntroScreenState, action: IntroScreenState.Action) {
        when (action) {
            IntroScreenState.Action.START_FOR_FREE -> {
                println("Starting for free...")
                _screenState.value = OnboardingScreenState()
            }
            IntroScreenState.Action.LOG_IN -> {
                println("Logging in...")
            }
        }
    }

    private fun handleOnboardingAction(screenState: OnboardingScreenState, action: OnboardingScreenState.Action) {
        when (action.type) {
            OnboardingScreenState.ActionType.SUBMIT -> {
                println("Submitting Onboarding Data...")
                val nextScreenState = screenState.nextScreen()
                if (nextScreenState.state != OnboardingScreenState.State.DONE) {
                    _screenState.value = nextScreenState
                } else {
                    _screenState.value = ProcessingScreenState()
                }
            }
            OnboardingScreenState.ActionType.SELECT -> {
                println("Selecting options...")
                if (action.option is Int) {
                    _screenState.value = screenState.updateWithSelectedOption(action.option)
                } else {
                    throw IllegalArgumentException("Unknown option selected")
                }
            }
        }
    }

    private fun handleProcessingAction(screenState: ProcessingScreenState, action: ProcessingScreenState.Action) {
        when (action) {
            ProcessingScreenState.Action.CREATE_AN_ACCOUNT -> {
                println("Creating an account...")
                _screenState.value = CreateAnAccountScreenState()
            }
            ProcessingScreenState.Action.MOVE_TO_NEXT_STEP -> {
                println("Move to next step...")
                _screenState.value = screenState.nextScreen()
            }
            ProcessingScreenState.Action.LOGIN_IN -> {
                println("Logging in from processing screen...")
            }
        }
    }

    private fun handleCreateAccountAction(screenState: CreateAnAccountScreenState, action: CreateAnAccountScreenState.Action) {
        when (action) {
            CreateAnAccountScreenState.Action.SOCIAL_LOGIN -> {
                println("Social login...")
            }
            CreateAnAccountScreenState.Action.EMAIL -> {
                println("Email login/sign-up...")
            }
        }
    }
}