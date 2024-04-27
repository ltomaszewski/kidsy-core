package com.growgenie.kidsyCore.model.screenState.screen.onboarding

import OnboardingModel
import OnboardingOption
import OnboardingScreenModel
import com.growgenie.kidsyCore.model.screenState.ScreenName
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.UserAction
import kotlinx.serialization.json.Json

data class OnboardingScreenState(val jsonInput: String = JSON_INPUT,
                                 val state: State = State.ONBOARDING,
                                 val index: Int = 0,
                                 val selectedOptions: List<OnboardingOption> = listOf()) : ScreenState {
    override val screenName: ScreenName = ScreenName.ONBOARDING

    enum class State { ONBOARDING, DONE }

    enum class ActionType {
        SUBMIT, SELECT
    }

    class Action(val type: ActionType, val option: Int? = null) : UserAction {
        companion object {
            fun create(type: ActionType, option: Int? = null): Action {
                return Action(type, option)
            }
        }
    }

    val onboarding: OnboardingModel
    val currentScreenModel: OnboardingScreenModel

    init {
        val json = Json { ignoreUnknownKeys = true } // Configure the Json to ignore unknown keys
        onboarding = json.decodeFromString(OnboardingModel.serializer(), jsonInput)
        currentScreenModel = onboarding.screens[index]
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

    companion object {
        const val JSON_INPUT = "{\"screens\":[{\"type\":\"Prompt\",\"id\":\"start-personalizing\",\"headline\":\"Answer a few questions to start personalizing your experience.\",\"submitTop\":\"Tap anywhere to continue.\"},{\"type\":\"Question\",\"id\":\"biggest-struggles\",\"headline\":\"Select your biggest parenting struggles.\",\"options\":[{\"id\":1,\"text\":\"Sleep\"},{\"id\":2,\"text\":\"Health\"},{\"id\":3,\"text\":\"Eating\"},{\"id\":4,\"text\":\"Behaviour\"}]},{\"type\":\"QuestionMultiselect\",\"id\":\"struggles-continue\",\"headline\":\"Select your biggest parenting struggles.\",\"options\":[{\"id\":1,\"text\":\"Stay alive\"},{\"id\":2,\"text\":\"Keep going\"},{\"id\":3,\"text\":\"No cash for therapy\"}],\"submit\":\"Continue\"},{\"type\":\"Question\",\"id\":\"sleep-time\",\"headline\":\"How long does it usually take to fall asleep?\",\"options\":[{\"id\":1,\"text\":\"5-15 minutes\"},{\"id\":2,\"text\":\"15-30 minutes\"},{\"id\":3,\"text\":\"30+ minutes\"}]}]}"
    }
}