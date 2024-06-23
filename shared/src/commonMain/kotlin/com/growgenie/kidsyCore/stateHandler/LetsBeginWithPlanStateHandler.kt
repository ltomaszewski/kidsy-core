package com.growgenie.kidsyCore.stateHandler

import com.growgenie.kidsyCore.UserSession.UserSession
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.screen.LetsBeginWithPlanScreenState
import com.growgenie.kidsyCore.model.screenState.screen.plan.PlanScreenState

class LetsBeginWithPlanStateHandler(private val userSession: UserSession) :
    StateHandler<LetsBeginWithPlanScreenState, LetsBeginWithPlanScreenState.Action> {

    init {
        println("LetsBeginWithPlanStateHandler initialized")
    }

    override fun handle(
        state: LetsBeginWithPlanScreenState,
        action: LetsBeginWithPlanScreenState.Action
    ): ScreenState {
        println("Handling action: ${action}")
        return when (action) {
            LetsBeginWithPlanScreenState.Action.BEGIN -> {
                println("Executing BEGIN action: Initializing plan")
                val planScreenState = PlanScreenState(userSession = userSession)
                userSession.addOrUpdatePlan(planScreenState.planModel.id)
                println("Plan initialized with id: ${planScreenState.planModel.id}")
                planScreenState
            }

            LetsBeginWithPlanScreenState.Action.CLOSE -> {
                println("Executing CLOSE action: Closing Lets Begin screen")
                println("Close lets begin")
                state
            }
        }
    }
}
