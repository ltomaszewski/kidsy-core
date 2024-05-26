package com.growgenie.kidsyCore.model.screenState.screen

import com.growgenie.kidsyCore.model.screenState.ScreenName
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.UserAction
import com.growgenie.kidsyCore.model.screenState.screen.plan.PlanModel

data class LetsBeginWithPlanScreenState(
    val plan: PlanModel,
    val state: State = State.START
) : ScreenState {
    override val screenName: ScreenName = ScreenName.INTRO

    enum class State {
        START
    }

    enum class Action: UserAction {
        BEGIN, CLOSE
    }

    val header = "Let’s begin your personalized program"
    val subtitle = "It’s an interactive series scientifically proven to improve your child sleep."
    val program_card_title
        get() = plan.headline
    val program_card_subTitle
        get() = plan.headlineBottom
    val program_card_decorative_graphic
        get () = plan.headlineImageName
    val program_card_submit = "Begin"
}