package com.growgenie.kidsyCore.model.screenState.screen.home.today

import com.growgenie.kidsyCore.model.screenState.ScreenName
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.UserAction
import com.growgenie.kidsyCore.sleepTracker.SleepSession
import com.growgenie.kidsyCore.sleepTracker.SleepTrackerManager
import com.growgenie.kidsyCore.utils.DateHelper

data class TodayScreenState(
    val selectedDate: Long = DateHelper().getCurrentUnixTimestamp(),
    val sleepTrackerManager: SleepTrackerManager
) : ScreenState {
    override val screenName: ScreenName = ScreenName.TODAY

    enum class State { START }

    enum class ActionType {
        ADD, EDIT, SELECT_DATE
    }

    class Action(
        val type: ActionType,
        val date: Long?,
        val sessionId: String?
    ) : UserAction

    val sessions: List<SleepSession>
        get() {
            return sleepTrackerManager.getSessionsForDate(selectedDate)
        }

    fun updateWithSelectedDate(date: Long): TodayScreenState {
        return copy(selectedDate = date)
    }
}