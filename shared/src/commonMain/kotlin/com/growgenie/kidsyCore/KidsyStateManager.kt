package com.growgenie.kidsyCore

import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.UserAction
import com.growgenie.kidsyCore.model.screenState.screen.*
import com.growgenie.kidsyCore.model.screenState.screen.onboarding.*
import com.growgenie.kidsyCore.model.screenState.screen.plan.PlanModel
import com.growgenie.kidsyCore.model.screenState.screen.plan.PlanScreenState
import com.growgenie.kidsyCore.model.screenState.screen.plan.Plans
import com.growgenie.kidsyCore.stateHandler.LetsBeginWithPlanStateHandler
import com.growgenie.kidsyCore.stateHandler.PlanScreenStateStateHandler
import com.growgenie.kidsyCore.utils.wrap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class KidsyStateManager {
    private val _screenState = MutableStateFlow<ScreenState>(IntroScreenState())
    val screenState = _screenState.asStateFlow().wrap()

    private val letsBeginStateHandler = LetsBeginWithPlanStateHandler()
    private val planStateHandler = PlanScreenStateStateHandler()

    fun executeAction(action: UserAction) {
        when (screenState.value) {
            is IntroScreenState -> handleIntroAction(screenState.value, action as IntroScreenState.Action)
            is OnboardingScreenState -> handleOnboardingAction(screenState.value, action as OnboardingScreenState.Action)
            is OnboardingProcessingScreenState -> handleProcessingAction(screenState.value, action as OnboardingProcessingScreenState.Action)
            is OnboardingSuccessScreenState -> handleOnboardingSuccessStateAction(screenState.value, action as OnboardingSuccessScreenState.Action)
            is CreateAnAccountScreenState -> handleCreateAccountAction(screenState.value, action as CreateAnAccountScreenState.Action)
            is LetsBeginWithPlanScreenState -> _screenState.value = letsBeginStateHandler.handle(screenState.value, action as LetsBeginWithPlanScreenState.Action)
            is PlanScreenState -> _screenState.value = planStateHandler.handle(screenState.value, action as PlanScreenState.Action)
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
                    _screenState.value = OnboardingSuccessScreenState()
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
            OnboardingScreenState.ActionType.TEXT_INPUT -> {
                println("TEXT_INPUT value...")
                val nextScreenState = screenState.nextScreen()
                if (nextScreenState.state != OnboardingScreenState.State.DONE) {
                    _screenState.value = nextScreenState
                } else {
                    _screenState.value = OnboardingSuccessScreenState()
                }
            }
        }
    }

    private fun handleProcessingAction(screenState: OnboardingProcessingScreenState, action: OnboardingProcessingScreenState.Action) {
        when (action) {
            OnboardingProcessingScreenState.Action.CREATE_AN_ACCOUNT -> {
                println("Creating an account...")
                _screenState.value = CreateAnAccountScreenState()
            }
            OnboardingProcessingScreenState.Action.MOVE_TO_NEXT_STEP -> {
                println("Move to next step...")
                _screenState.value = screenState.nextScreen()
            }
            OnboardingProcessingScreenState.Action.LOGIN_IN -> {
                println("Logging in from processing screen...")
            }
        }
    }

    private fun handleOnboardingSuccessStateAction(screenState: OnboardingSuccessScreenState, action: OnboardingSuccessScreenState.Action) {
        when (action) {
            OnboardingSuccessScreenState.Action.CREATE_ACCOUNT -> {
                _screenState.value = CreateAnAccountScreenState()
            }
            OnboardingSuccessScreenState.Action.LOG_IN -> {
                println("LOG IN...")
            }
        }
    }

    private fun handleCreateAccountAction(screenState: CreateAnAccountScreenState, action: CreateAnAccountScreenState.Action) {
        when (action.type) {
            CreateAnAccountScreenState.ActionType.LoggedIn -> {
                println("Social LoggedIn with " + action.socialMedia + " userIdentifier " + action.userIdentifier + " fullName " + action.fullName + " email " + action.email)
                val json = Json { ignoreUnknownKeys = true } // Configure the Json to ignore unknown keys
                val planModel = json.decodeFromString(PlanModel.serializer(), Plans.day0)
                _screenState.value = LetsBeginWithPlanScreenState(planModel)
            }
        }
    }
}