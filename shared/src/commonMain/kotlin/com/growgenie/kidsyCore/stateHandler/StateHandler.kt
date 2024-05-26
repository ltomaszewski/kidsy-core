package com.growgenie.kidsyCore.stateHandler

import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.UserAction

interface StateHandler<S : ScreenState, A : UserAction> {
    fun handle(state: S, action: A): ScreenState
}