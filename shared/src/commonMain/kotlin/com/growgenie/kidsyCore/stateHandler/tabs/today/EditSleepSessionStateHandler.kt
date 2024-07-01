package com.growgenie.kidsyCore.stateHandler.tabs.today

import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.screen.home.today.EditSleepSessionScreenState
import com.growgenie.kidsyCore.sleepTracker.SleepTrackerManager
import com.growgenie.kidsyCore.stateHandler.StateHandler

class EditSleepSessionStateHandler(private val sleepTrackerManager: SleepTrackerManager) :
    StateHandler<EditSleepSessionScreenState, EditSleepSessionScreenState.Action> {
    override fun handle(
        state: EditSleepSessionScreenState,
        action: EditSleepSessionScreenState.Action
    ): ScreenState {
        return when (action.type) {
            EditSleepSessionScreenState.ActionType.EDIT -> {
                sleepTrackerManager.modifySession(
                    state.sessionId,
                    action.startTime,
                    action.endTime,
                    action.sleepSessionType
                )
                state.copy(state = EditSleepSessionScreenState.State.SAVED)
            }
        }
    }
}