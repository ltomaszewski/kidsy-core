package com.growgenie.kidsyCore.stateHandler

import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.screen.LetsBeginWithPlanScreenState
import com.growgenie.kidsyCore.model.screenState.screen.plan.PlanScreenState

class LetsBeginWithPlanStateHandler: StateHandler<LetsBeginWithPlanScreenState, LetsBeginWithPlanScreenState.Action> {
    override fun handle(
        state: LetsBeginWithPlanScreenState,
        action: LetsBeginWithPlanScreenState.Action
    ): ScreenState {
        return when (action) {
            LetsBeginWithPlanScreenState.Action.BEGIN -> {
                PlanScreenState()
            }
            LetsBeginWithPlanScreenState.Action.CLOSE -> {
                println("Close lets begin")
                state
            }
        }
    }
}