package com.growgenie.kidsyCore.stateHandler

import com.growgenie.kidsyCore.UserSession.UserSession
import com.growgenie.kidsyCore.UserSession.UserSessionOption
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.screen.plan.PlanScreenState

class PlanScreenStateStateHandler(val userSession: UserSession) :
    StateHandler<PlanScreenState, PlanScreenState.Action> {

    init {
        println("PlanScreenStateStateHandler initialized")
    }

    override fun handle(state: PlanScreenState, action: PlanScreenState.Action): ScreenState {
        println("Handling action: ${action.type}")
        return when (action.type) {
            PlanScreenState.ActionType.SUBMIT -> {
                println("Executing SUBMIT action: Submitting plan data")
                val nextScreenState = state.nextScreen()
                if (nextScreenState.state != PlanScreenState.State.DONE) {
                    println("Plan not done, moving to next screen state")
                    nextScreenState
                } else {
                    println("Plan is done")
                    state
                }
            }

            PlanScreenState.ActionType.SELECT -> {
                println("Executing SELECT action: Selecting options")
                userSession.updateCollectedData(
                    state.planModel.id,
                    state.currentScreen.id,
                    UserSessionOption().apply {
                        this.jsonId = state.currentScreen.id
                        this.text = action.option.toString()
                    }
                )

                if (action.option is Int) {
                    println("Option is an integer, updating state with selected option")
                    state.updateWithSelectedOption(action.option)
                } else {
                    println("Unknown option selected, throwing exception")
                    throw IllegalArgumentException("Unknown option selected")
                }
            }

            PlanScreenState.ActionType.TEXT_INPUT -> {
                println("Executing TEXT_INPUT action: Processing text input")
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
                    println("Plan not done, moving to next screen state")
                    nextScreenState
                } else {
                    println("Plan is done")
                    state
                }
            }
        }
    }
}
