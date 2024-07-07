package com.growgenie.kidsyCore.stateHandler

import com.growgenie.kidsyCore.UserSession.UserSession
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.screen.LetsBeginWithPlanScreenState
import com.growgenie.kidsyCore.model.screenState.screen.home.HomeTabBarScreenState
import com.growgenie.kidsyCore.model.screenState.screen.plan.PlanScreenState
import com.growgenie.kidsyCore.sleepTracker.SleepTrackerManager

class LetsBeginWithPlanStateHandler(
    private val userSession: UserSession,
    private val sleepTrackerManager: SleepTrackerManager
) :
    StateHandler<LetsBeginWithPlanScreenState, LetsBeginWithPlanScreenState.Action> {

    init {
        println("LetsBeginWithPlanStateHandler initialized")
    }

    override fun handle(
        state: LetsBeginWithPlanScreenState,
        action: LetsBeginWithPlanScreenState.Action
    ): ScreenState {
        println("LetsBeginWithPlanStateHandler Handling action: ${action}")
        return when (action) {
            LetsBeginWithPlanScreenState.Action.BEGIN -> {
                println("LetsBeginWithPlanStateHandler Executing BEGIN action: Initializing plan")
                val planScreenState = PlanScreenState(userSession = userSession)
                userSession.addOrUpdatePlan(planScreenState.planModel.id)
                println("LetsBeginWithPlanStateHandler Plan initialized with id: ${planScreenState.planModel.id}")
                planScreenState
            }

            LetsBeginWithPlanScreenState.Action.CLOSE -> {
                println("LetsBeginWithPlanStateHandler Executing CLOSE action: Closing Lets Begin screen")
                println("LetsBeginWithPlanStateHandler Close lets begin")
                HomeTabBarScreenState()
            }
        }
    }
}
