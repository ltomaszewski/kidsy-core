package com.growgenie.kidsyCore.stateHandler

import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.screen.plan.PlanScreenState

class PlanScreenStateStateHandler: StateHandler<PlanScreenState, PlanScreenState.Action>  {
    override fun handle(state: PlanScreenState, action: PlanScreenState.Action): ScreenState {
        return when (action.type) {
            PlanScreenState.ActionType.SUBMIT -> {
                println("Submitting Plan Data...")
                val nextScreenState = state.nextScreen()
                if (nextScreenState.state != PlanScreenState.State.DONE) {
                    nextScreenState
                } else {
                    print("Plan is done");
                    state
                }
            }
            PlanScreenState.ActionType.SELECT -> {
                println("Selecting options...")
                if (action.option is Int) {
                    state.updateWithSelectedOption(action.option)
                } else {
                    throw IllegalArgumentException("Unknown option selected")
                }
            }
            PlanScreenState.ActionType.TEXT_INPUT -> {
                println("TEXT_INPUT value...")
                val nextScreenState = state.nextScreen()
                if (nextScreenState.state != PlanScreenState.State.DONE) {
                    nextScreenState
                } else {
                    print("Plan is done")
                    state
                }
            }
        }
    }
}