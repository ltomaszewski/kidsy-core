package com.growgenie.kidsyCore

import com.growgenie.kidsyCore.model.screenState.screen.IntroScreenState
import com.growgenie.kidsyCore.model.screenState.screen.onboarding.OnboardingScreenState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class CommonGreetingTest {

    @Test
    fun testScreenStateTransitions() = runTest {
        val stateManager = KidsyStateManager()

        // Initial State
        assertTrue(stateManager.screenState.value is IntroScreenState)

        // Execute action to transition to OnboardingScreenState
        stateManager.executeAction(IntroScreenState.Action.START_FOR_FREE)
        assertTrue(stateManager.screenState.value is OnboardingScreenState)

        val onboardingScreenState = stateManager.screenState.value as OnboardingScreenState
        assertTrue (onboardingScreenState.index == 0)

        stateManager.executeAction(OnboardingScreenState.Action(OnboardingScreenState.ActionType.SUBMIT))
        assertTrue(stateManager.screenState.value is OnboardingScreenState)

        val onboardingScreenStateAfterFirstSubmit = stateManager.screenState.value as OnboardingScreenState
        assertTrue (onboardingScreenStateAfterFirstSubmit.index == 1)

        stateManager.executeAction(OnboardingScreenState.Action(OnboardingScreenState.ActionType.SELECT, 1))
        stateManager.executeAction(OnboardingScreenState.Action(OnboardingScreenState.ActionType.SELECT, 2))
        assertTrue(stateManager.screenState.value is OnboardingScreenState)

        val onboardingScreenStateAfterMultiSelect = stateManager.screenState.value as OnboardingScreenState
        assertTrue (onboardingScreenStateAfterMultiSelect.index == 1)
        assertTrue (onboardingScreenStateAfterMultiSelect.selectedOptions.size == 2)
    }
}