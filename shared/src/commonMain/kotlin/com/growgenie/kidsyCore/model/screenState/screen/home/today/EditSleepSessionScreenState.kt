package com.growgenie.kidsyCore.model.screenState.screen.home.today

import com.growgenie.kidsyCore.model.screenState.ScreenName
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.UserAction
import com.growgenie.kidsyCore.sleepTracker.SleepSessionType

data class EditSleepSessionScreenState(
    val sessionId: String,
    val state: State = State.START
) : ScreenState {
    override val screenName: ScreenName = ScreenName.SLEEP_SESSION_EDIT

    enum class State {
        START, SAVED
    }

    enum class ActionType {
        EDIT
    }

    class Action(
        val type: ActionType,
        val startTime: Long,
        val endTime: Long,
        val sleepSessionType: SleepSessionType
    ) : UserAction


    val cancel = "Cancel"
    val save = "Save"
    val headline = "Edit Session"
    val startTime = "Start Time"
    val endTime = "End Time"
}