package com.growgenie.kidsyCore.stateHandler.PlanScreenStateStateHandler

import com.growgenie.kidsyCore.UserSession.UserSession
import com.growgenie.kidsyCore.UserSession.UserSessionOption
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.screen.home.HomeTabBarScreenState
import com.growgenie.kidsyCore.model.screenState.screen.plan.PlanScreenState
import com.growgenie.kidsyCore.sleepTracker.SleepTrackerManager
import com.growgenie.kidsyCore.stateHandler.StateHandler

class PlanScreenStateStateHandler(
    val userSession: UserSession,
    val sleepTrackerManager: SleepTrackerManager
) :
    StateHandler<PlanScreenState, PlanScreenState.Action> {
    private val placeholderReplacer = PlaceholderReplacer()

    init {
        println("PlanScreenStateStateHandler initialized")
    }

    override fun handle(state: PlanScreenState, action: PlanScreenState.Action): ScreenState {
        println("PlanScreenStateStateHandler Handling action: ${action.type}")
        return when (action.type) {
            PlanScreenState.ActionType.SUBMIT -> {
                println("PlanScreenStateStateHandler Executing SUBMIT action: Submitting plan data")
                var nextScreenState = state.nextScreen()
                if (nextScreenState.state != PlanScreenState.State.DONE) {
                    println("PlanScreenStateStateHandler Plan not done, moving to next screen state")
                    nextScreenState
                } else {
                    println("PlanScreenStateStateHandler Plan is done")
                    HomeTabBarScreenState(sleepTrackerManager = sleepTrackerManager)
                }
            }

            PlanScreenState.ActionType.SELECT -> {
                println("PlanScreenStateStateHandler Executing SELECT action: Selecting options")
                userSession.updateCollectedData(
                    state.planModel.id,
                    state.currentScreen.id,
                    UserSessionOption().apply {
                        this.jsonId = state.currentScreen.id
                        this.text = action.option.toString()
                    }
                )

                if (action.option is Int) {
                    println("PlanScreenStateStateHandler Option is an integer, updating state with selected option")
                    state.updateWithSelectedOption(action.option)
                } else {
                    println("PlanScreenStateStateHandler Unknown option selected, throwing exception")
                    throw IllegalArgumentException("Unknown option selected")
                }
            }

            PlanScreenState.ActionType.TEXT_INPUT -> {
                println("PlanScreenStateStateHandler Executing TEXT_INPUT action: Processing text input")
                userSession.updateCollectedData(
                    state.planModel.id,
                    state.currentScreen.id,
                    UserSessionOption().apply {
                        this.jsonId = state.currentScreen.id
                        this.text = action.text.toString()
                    }
                )

                val nextScreenState = state.nextScreen()
                if (nextScreenState.state != PlanScreenState.State.DONE) {
                    println("PlanScreenStateStateHandler Plan not done, moving to next screen state")
                    nextScreenState
                } else {
                    println("PlanScreenStateStateHandler Plan is done")
                    state
                }
            }
        }
    }
}
