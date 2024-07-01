package com.growgenie.kidsyCore.stateHandler.tabs.today

import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.screen.home.today.AddSleepSessionScreenState
import com.growgenie.kidsyCore.sleepTracker.SleepTrackerManager
import com.growgenie.kidsyCore.stateHandler.StateHandler

class AddSleepSessionStateHandler(private val sleepTrackerManager: SleepTrackerManager) :
    StateHandler<AddSleepSessionScreenState, AddSleepSessionScreenState.Action> {
    override fun handle(
        state: AddSleepSessionScreenState,
        action: AddSleepSessionScreenState.Action
    ): ScreenState {
        return when (action.type) {
            AddSleepSessionScreenState.ActionType.SAVE -> {
                sleepTrackerManager.addSession(
                    action.startTime,
                    action.endTime,
                    action.sleepSessionType
                )
                state.copy(state = AddSleepSessionScreenState.State.SAVED)
            }
        }
    }
}