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
        const val JSON_INPUT = "{\"screens\":[{\"type\":\"Prompt\",\"id\":\"1_pick_type_prompt\",\"headline\":\"Answer a few questions to start personalizing your experience.\",\"submitTop\":\"Tap anywhere to continue.\"},{\"type\":\"QuestionMultiselect\",\"id\":\"2_forced_selection_to_submit\",\"headline\":\"Select your biggest parenting struggles.\",\"options\":[{\"id\":0,\"text\":\"Sleep\"},{\"id\":1,\"text\":\"Health\"},{\"id\":2,\"text\":\"Eating\"},{\"id\":3,\"text\":\"Behaviour\"}],\"submit\":\"Continue\"},{\"type\":\"Question\",\"id\":\"3_select_top_priority\",\"headline\":\"Select your top priority.\",\"options\":[{\"id\":0,\"text\":\"Sleep\"},{\"id\":1,\"text\":\"Health\"}],\"submit\":\"Continue\"},{\"type\":\"Question\",\"id\":\"4_kids_age\",\"headline\":\"What best describe your kid’s age?\",\"options\":[{\"id\":0,\"text\":\"Newborn (0-3mo)\"},{\"id\":1,\"text\":\"Baby (3-12mo)\"},{\"id\":2,\"text\":\"Toddler (1-3 year)\"},{\"id\":3,\"text\":\"Preschool (3-5 year)\"},{\"id\":4,\"text\":\"School (5-11 year)\"},{\"id\":5,\"text\":\"Teen (11+ year)\"}],\"submit\":\"Continue\"},{\"type\":\"Prompt\",\"id\":\"5_here_s_what\",\"headlineTop\":\"\",\"headline\":\"Here’s what our members are saying:\",\"headlineBottom\":\"\",\"submitTop\":\"Based on a study of members who use Kidsy 5 times per week.\",\"submit\":\"Continue\"},{\"type\":\"QuestionMultiselect\",\"id\":\"6_sleep_problems\",\"headline\":\"What sleep problems are you facing?\",\"options\":[{\"id\":0,\"text\":\"Frequent wake-ups\"},{\"id\":1,\"text\":\"Time to fall asleep\"},{\"id\":2,\"text\":\"Early wakeups\"},{\"id\":3,\"text\":\"Chaotic nap schedule\"}],\"submit\":\"Continue\"},{\"type\":\"Question\",\"id\":\"7_fall_asleep_time\",\"headline\":\"How long does it usually take to fall asleep?\",\"options\":[{\"id\":0,\"text\":\"5-15 minutes\"},{\"id\":1,\"text\":\"15-30 minutes\"},{\"id\":2,\"text\":\"30+ minutes\"}],\"submit\":\"Continue\"},{\"type\":\"Question\",\"id\":\"8_naps_toddler\",\"headline\":\"How many naps your toddler have?\",\"options\":[{\"id\":0,\"text\":\"1\"},{\"id\":1,\"text\":\"2\"},{\"id\":2,\"text\":\"None\"}],\"submit\":\"Continue\"},{\"type\":\"Question\",\"id\":\"9_role\",\"headline\":\"What best describe your role?\",\"options\":[{\"id\":0,\"text\":\"Mom\"},{\"id\":1,\"text\":\"Dad\"},{\"id\":2,\"text\":\"Caregiver\"}],\"submit\":\"Continue\"},{\"type\":\"Question\",\"id\":\"10_single_parent\",\"headline\":\"Are you a single parent?\",\"options\":[{\"id\":0,\"text\":\"Yes\"},{\"id\":1,\"text\":\"No\"}],\"submit\":\"Continue\"},{\"type\":\"Prompt\",\"id\":\"11_did_you_know\",\"headlineTop\":\"Did you know?\",\"headline\":\"Kidsy helped 320,000 single moms improve their kids sleep.\",\"headlineBottom\":\"As of July 2024\",\"submit\":\"Continue\"},{\"type\":\"Question\",\"id\":\"12_learning_new_parenting_techinques\",\"headline\":\"When would you like to learn new parenting techinques?\",\"options\":[{\"id\":0,\"text\":\"Morning\"},{\"id\":1,\"text\":\"Afternoon\"},{\"id\":2,\"text\":\"Evening\"}],\"submit\":\"Continue\"}]}"
    }
}