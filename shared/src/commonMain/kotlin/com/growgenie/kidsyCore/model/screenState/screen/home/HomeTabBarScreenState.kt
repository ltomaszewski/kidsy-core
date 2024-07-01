package com.growgenie.kidsyCore.model.screenState.screen.home

import com.growgenie.kidsyCore.model.screenState.ScreenName
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.UserAction
import com.growgenie.kidsyCore.model.screenState.screen.home.today.TodayScreenState
import com.growgenie.kidsyCore.sleepTracker.SleepTrackerManager

data class HomeTabBarScreenState(
    val state: State = State.TODAY,
    val sleepTrackerManager: SleepTrackerManager
) : ScreenState {
    override val screenName: ScreenName = ScreenName.HOME

    enum class State {
        TODAY, PLANS, SINGLE, SCHEDULE, PROFILE
    }

    enum class ActionType {
        OPEN_TAB
    }

    class Action(val type: ActionType, val newTabBarState: State) : UserAction

    data class TabScreenState(
        val tabState: State,
        val screenState: ScreenState,
        val tabBarItem: TabBarItem
    )

    data class TabBarItem(
        val name: String,
        val imageName: String
    )

    val tabBarItems: List<TabBarItem> = listOf(
        TabBarItem(
            "Today",
            "today_home"
        ),
        TabBarItem(
            "Plans",
            "plans"
        ),
        TabBarItem(
            "Single",
            "single_tab"
        ),
        TabBarItem(
            "Schedule",
            "schedule_tab"
        ),
        TabBarItem(
            "Profile",
            "profile_tab"
        )
    )

    val currentTabScreenState: TabScreenState
        get() {
            return when (state) {
                State.TODAY -> TabScreenState(
                    State.TODAY,
                    TodayScreenState(sleepTrackerManager = sleepTrackerManager),
                    TabBarItem(
                        "Today",
                        "today_home"
                    )
                )

                State.PLANS -> TabScreenState(
                    State.PLANS,
                    PlansTabScreenState(),
                    TabBarItem(
                        "Plans",
                        "plans"
                    )
                )

                State.SINGLE -> TabScreenState(
                    State.SINGLE,
                    SinglesTabScreenState(),
                    TabBarItem(
                        "Single",
                        "single_tab"
                    )
                )

                State.SCHEDULE -> TabScreenState(
                    State.SCHEDULE,
                    ScheduleTabScreenState(),
                    TabBarItem(
                        "Schedule",
                        "schedule_tab"
                    )
                )

                State.PROFILE -> TabScreenState(
                    State.PROFILE,
                    ProfileTabScreenState(),
                    TabBarItem(
                        "Profile",
                        "profile_tab"
                    )
                )
            }
        }
}