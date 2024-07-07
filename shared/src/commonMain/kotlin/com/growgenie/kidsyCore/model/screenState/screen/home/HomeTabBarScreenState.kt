package com.growgenie.kidsyCore.model.screenState.screen.home

import com.growgenie.kidsyCore.model.screenState.ScreenName
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.UserAction
import com.growgenie.kidsyCore.model.screenState.screen.home.today.TodayScreenState

data class HomeTabBarScreenState(
    val state: State = State.TODAY
) : ScreenState {
    override val screenName: ScreenName = ScreenName.HOME

    enum class State {
        TODAY, PLANS, SINGLE, SCHEDULE, PROFILE
    }

    enum class ActionType {
        OPEN_TAB
    }

    class Action(val type: ActionType, val newTabBarIndex: Int) : UserAction

    data class TabScreenState(
        val screenState: ScreenState,
        val tabBarItem: TabBarItem
    )

    data class TabBarItem(
        val tabState: State,
        val name: String,
        val imageName: String
    )

    val tabBarItems: List<TabBarItem> = listOf(
        TabBarItem(
            State.TODAY,
            "Today",
            "today_home"
        ),
        TabBarItem(
            State.PLANS,
            "Plans",
            "plans"
        ),
        TabBarItem(
            State.SINGLE,
            "Single",
            "single_tab"
        ),
        TabBarItem(
            State.SCHEDULE,
            "Schedule",
            "schedule_tab"
        ),
        TabBarItem(
            State.PROFILE,
            "Profile",
            "profile_tab"
        )
    )

    val currentTabScreenState: TabScreenState
        get() {
            return when (state) {
                State.TODAY -> TabScreenState(
                    TodayScreenState(),
                    TabBarItem(
                        State.TODAY,
                        "Today",
                        "today_home"
                    )
                )

                State.PLANS -> TabScreenState(
                    PlansTabScreenState(),
                    TabBarItem(
                        State.PLANS,
                        "Plans",
                        "plans"
                    )
                )

                State.SINGLE -> TabScreenState(
                    SinglesTabScreenState(),
                    TabBarItem(
                        State.SINGLE,
                        "Single",
                        "single_tab"
                    )
                )

                State.SCHEDULE -> TabScreenState(
                    ScheduleTabScreenState(),
                    TabBarItem(
                        State.SCHEDULE,
                        "Schedule",
                        "schedule_tab"
                    )
                )

                State.PROFILE -> TabScreenState(
                    ProfileTabScreenState(),
                    TabBarItem(
                        State.PROFILE,
                        "Profile",
                        "profile_tab"
                    )
                )
            }
        }
}