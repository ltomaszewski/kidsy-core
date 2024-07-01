package com.growgenie.kidsyCore

import com.growgenie.kidsyCore.UserSession.UserSession
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.UserAction
import com.growgenie.kidsyCore.model.screenState.screen.CreateAnAccountScreenState
import com.growgenie.kidsyCore.model.screenState.screen.IntroScreenState
import com.growgenie.kidsyCore.model.screenState.screen.LetsBeginWithPlanScreenState
import com.growgenie.kidsyCore.model.screenState.screen.home.HomeTabBarScreenState
import com.growgenie.kidsyCore.model.screenState.screen.home.today.AddSleepSessionScreenState
import com.growgenie.kidsyCore.model.screenState.screen.home.today.EditSleepSessionScreenState
import com.growgenie.kidsyCore.model.screenState.screen.home.today.TodayScreenState
import com.growgenie.kidsyCore.model.screenState.screen.onboarding.OnboardingProcessingScreenState
import com.growgenie.kidsyCore.model.screenState.screen.onboarding.OnboardingScreenState
import com.growgenie.kidsyCore.model.screenState.screen.onboarding.OnboardingSuccessScreenState
import com.growgenie.kidsyCore.model.screenState.screen.plan.PlanModel
import com.growgenie.kidsyCore.model.screenState.screen.plan.PlanScreenState
import com.growgenie.kidsyCore.model.screenState.screen.plan.Plans
import com.growgenie.kidsyCore.sleepTracker.SleepTrackerManager
import com.growgenie.kidsyCore.stateHandler.HomeTabBarStateHandler
import com.growgenie.kidsyCore.stateHandler.LetsBeginWithPlanStateHandler
import com.growgenie.kidsyCore.stateHandler.OnboardingStateHandler
import com.growgenie.kidsyCore.stateHandler.PlanScreenStateStateHandler.PlanScreenStateStateHandler
import com.growgenie.kidsyCore.stateHandler.tabs.today.AddSleepSessionStateHandler
import com.growgenie.kidsyCore.stateHandler.tabs.today.EditSleepSessionStateHandler
import com.growgenie.kidsyCore.stateHandler.tabs.today.TodayStateHandler
import com.growgenie.kidsyCore.utils.RealmHelper
import com.growgenie.kidsyCore.utils.wrap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class KidsyStateManager {
    // State
    private val _screenState = MutableStateFlow<ScreenState>(IntroScreenState())
    val screenState = _screenState.asStateFlow().wrap()

    // Services
    private val realmHelper = RealmHelper()
    private val userSession = UserSession(realmHelper)  // User session instance
    private val sleepTrackerManager = SleepTrackerManager(realmHelper)

    // StateHandlers
    private val onboardingStateHandler = OnboardingStateHandler(userSession)
    private val letsBeginStateHandler =
        LetsBeginWithPlanStateHandler(userSession, sleepTrackerManager)
    private val planStateHandler = PlanScreenStateStateHandler(userSession, sleepTrackerManager)
    private val homeTabBarStateHandler = HomeTabBarStateHandler(userSession)
    private val todayStateHandler = TodayStateHandler()
    private val addSleepSessionStateHandler = AddSleepSessionStateHandler(sleepTrackerManager)
    private val editSleepSessionStateHandler = EditSleepSessionStateHandler(sleepTrackerManager)

    private fun getInitialScreenState(): ScreenState {
        return if (userSession.hasFinishedOnboarding) {
            HomeTabBarScreenState(sleepTrackerManager = sleepTrackerManager)
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

            is OnboardingScreenState -> _screenState.value =
                onboardingStateHandler.handle(
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

            is LetsBeginWithPlanScreenState -> _screenState.value =
                letsBeginStateHandler.handle(
                    screenState.value,
                    action as LetsBeginWithPlanScreenState.Action
                )

            is PlanScreenState -> _screenState.value =
                planStateHandler.handle(
                    screenState.value,
                    action as PlanScreenState.Action
                )

            is HomeTabBarScreenState -> _screenState.value =
                homeTabBarStateHandler.handle(
                    screenState.value,
                    action as HomeTabBarScreenState.Action
                )

            is TodayScreenState -> _screenState.value =
                todayStateHandler.handle(
                    screenState.value,
                    action as TodayScreenState.Action
                )

            is AddSleepSessionScreenState -> _screenState.value =
                addSleepSessionStateHandler.handle(
                    screenState.value,
                    action as AddSleepSessionScreenState.Action
                )

            is EditSleepSessionScreenState -> _screenState.value =
                editSleepSessionStateHandler.handle(
                    screenState.value,
                    action as EditSleepSessionScreenState.Action
                )

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
                _screenState.value = CreateAnAccountScreenState()
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