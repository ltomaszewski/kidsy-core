package com.growgenie.kidsyCore.stateHandler.tabs.today

import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.screen.home.today.AddSleepSessionScreenState
import com.growgenie.kidsyCore.model.screenState.screen.home.today.EditSleepSessionScreenState
import com.growgenie.kidsyCore.model.screenState.screen.home.today.TodayScreenState
import com.growgenie.kidsyCore.stateHandler.StateHandler

class TodayStateHandler : StateHandler<TodayScreenState, TodayScreenState.Action> {
    override fun handle(
        state: TodayScreenState,
        action: TodayScreenState.Action
    ): ScreenState {
        return when (action.type) {
            TodayScreenState.ActionType.ADD ->
                AddSleepSessionScreenState()

            TodayScreenState.ActionType.EDIT -> {
                action.sessionId?.let {
                    EditSleepSessionScreenState(it)
                } ?: state
            }

            TodayScreenState.ActionType.SELECT_DATE -> {
                action.date?.let {
                    state.updateWithSelectedDate(it)
                } ?: state
            }
        }
    }
}