package com.growgenie.kidsyCore.model.screenState.screen.onboarding

import OnboardingModel
import com.growgenie.kidsyCore.model.screenState.ScreenName
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.UserAction
import com.growgenie.kidsyCore.model.screenState.screen.Option
import com.growgenie.kidsyCore.model.screenState.screen.ScreenModel
import com.growgenie.kidsyCore.model.screenState.screen.plan.Plans
import kotlinx.serialization.json.Json

data class OnboardingScreenState(
    val jsonInput: String = Plans.onboarding,
    val state: State = State.ONBOARDING,
    val index: Int = 0,
    val selectedOptions: List<Option> = listOf()
) : ScreenState {
    override val screenName: ScreenName = ScreenName.ONBOARDING

    enum class State { ONBOARDING, DONE }

    enum class ActionType {
        SUBMIT, SELECT, TEXT_INPUT
    }

    class Action(val type: ActionType, val option: Int? = null, val text: String? = null) :
        UserAction

    val onboarding: OnboardingModel
    val currentScreenModel: ScreenModel
    val size: Int

    init {
        val json = Json { ignoreUnknownKeys = true } // Configure the Json to ignore unknown keys
        onboarding = json.decodeFromString(OnboardingModel.serializer(), jsonInput)
        currentScreenModel = onboarding.screens[index]
        size = onboarding.screens.size
    }

    // Method to create a new state with increased index
    fun nextScreen(): OnboardingScreenState {
        return if (index < onboarding.screens.size - 1) {
            copy(index = index + 1, selectedOptions = listOf())
        } else {
            copy(state = State.DONE, selectedOptions = listOf())
        }
    }

    fun updateWithSelectedOption(optionId: Int): OnboardingScreenState {
        val foundOption = currentScreenModel.options.firstOrNull { it.id == optionId }

        if (foundOption != null) {
            val newSelectedOptions = selectedOptions.toMutableList()
            if (!newSelectedOptions.contains(foundOption)) {
                newSelectedOptions.add(foundOption)
            }

            return copy(selectedOptions = newSelectedOptions)
        } else {
            throw IllegalArgumentException("Option with ID $optionId not found.")
        }
    }
}