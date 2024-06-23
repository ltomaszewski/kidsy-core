package com.growgenie.kidsyCore

import com.growgenie.kidsyCore.UserSession.UserSession
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.UserAction
import com.growgenie.kidsyCore.model.screenState.screen.CreateAnAccountScreenState
import com.growgenie.kidsyCore.model.screenState.screen.IntroScreenState
import com.growgenie.kidsyCore.model.screenState.screen.LetsBeginWithPlanScreenState
import com.growgenie.kidsyCore.model.screenState.screen.onboarding.OnboardingProcessingScreenState
import com.growgenie.kidsyCore.model.screenState.screen.onboarding.OnboardingScreenState
import com.growgenie.kidsyCore.model.screenState.screen.onboarding.OnboardingSuccessScreenState
import com.growgenie.kidsyCore.model.screenState.screen.plan.PlanModel
import com.growgenie.kidsyCore.model.screenState.screen.plan.PlanScreenState
import com.growgenie.kidsyCore.model.screenState.screen.plan.Plans
import com.growgenie.kidsyCore.stateHandler.LetsBeginWithPlanStateHandler
import com.growgenie.kidsyCore.stateHandler.OnboardingStateHandler
import com.growgenie.kidsyCore.stateHandler.PlanScreenStateStateHandler.PlanScreenStateStateHandler
import com.growgenie.kidsyCore.utils.RealmHelper
import com.growgenie.kidsyCore.utils.wrap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class KidsyStateManager {
    private val realmHelper = RealmHelper()
    private val userSession = UserSession(realmHelper)  // User session instance
    private val _screenState = MutableStateFlow<ScreenState>(IntroScreenState())
    val screenState = _screenState.asStateFlow().wrap()

    private val onboardingStateHandler = OnboardingStateHandler(userSession)
    private val letsBeginStateHandler = LetsBeginWithPlanStateHandler(userSession)
    private val planStateHandler = PlanScreenStateStateHandler(userSession)

    private fun getInitialScreenState(): ScreenState {
        return if (userSession.hasFinishedOnboarding) {
            PlanScreenState(userSession = userSession)  // Or any other initial state after onboarding
        } else {
            IntroScreenState()
        }
    }

    fun executeAction(action: UserAction) {
        when (screenState.value) {
            is IntroScreenState -> handleIntroAction(
                screenState.value,
                action as IntroScreenState.Action
            )

            is OnboardingScreenState -> _screenState.value = onboardingStateHandler.handle(
                screenState.value,
                action as OnboardingScreenState.Action
            )

            is OnboardingProcessingScreenState -> handleProcessingAction(
                screenState.value,
                action as OnboardingProcessingScreenState.Action
            )

            is OnboardingSuccessScreenState -> handleOnboardingSuccessStateAction(
                screenState.value,
                action as OnboardingSuccessScreenState.Action
            )

            is CreateAnAccountScreenState -> handleCreateAccountAction(
                screenState.value,
                action as CreateAnAccountScreenState.Action
            )

            is LetsBeginWithPlanScreenState -> _screenState.value = letsBeginStateHandler.handle(
                screenState.value,
                action as LetsBeginWithPlanScreenState.Action
            )

            is PlanScreenState -> _screenState.value =
                planStateHandler.handle(screenState.value, action as PlanScreenState.Action)

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


    private fun handleProcessingAction(
        screenState: OnboardingProcessingScreenState,
        action: OnboardingProcessingScreenState.Action
    ) {
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

    private fun handleOnboardingSuccessStateAction(
        screenState: OnboardingSuccessScreenState,
        action: OnboardingSuccessScreenState.Action
    ) {
        when (action) {
            OnboardingSuccessScreenState.Action.CREATE_ACCOUNT -> {
                _screenState.value = CreateAnAccountScreenState()
            }

            OnboardingSuccessScreenState.Action.LOG_IN -> {
                println("LOG IN...")
            }
        }
    }

    private fun handleCreateAccountAction(
        screenState: CreateAnAccountScreenState,
        action: CreateAnAccountScreenState.Action
    ) {
        when (action.type) {
            CreateAnAccountScreenState.ActionType.LoggedIn -> {
                println("Social LoggedIn with " + action.socialMedia + " userIdentifier " + action.userIdentifier + " fullName " + action.fullName + " email " + action.email)
                val json =
                    Json { ignoreUnknownKeys = true } // Configure the Json to ignore unknown keys
                val planModel = json.decodeFromString(PlanModel.serializer(), Plans.day0)
                userSession.setUserInfo(
                    action.userIdentifier ?: "-1",
                    action.fullName ?: "-1",
                    action.email ?: "-1"
                )
                _screenState.value = LetsBeginWithPlanScreenState(planModel)
            }
        }
    }
}