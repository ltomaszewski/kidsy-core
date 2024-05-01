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
    val size : Int

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

    companion object {
        const val JSON_INPUT = "{\"screens\":[{\"type\":\"Prompt\",\"id\":\"start_personalization\",\"headline\":\"Answer a few questions to start personalizing your experience.\",\"submitTop\":\"Tap anywhere to continue.\"},{\"type\":\"QuestionMultiselect\",\"id\":\"select_struggles\",\"headline\":\"Select your biggest parenting struggles.\",\"options\":[{\"id\":1,\"text\":\"Sleep\"},{\"id\":2,\"text\":\"Health\"},{\"id\":3,\"text\":\"Eating\"},{\"id\":4,\"text\":\"Behaviour\"}],\"submit\":\"Continue\"},{\"type\":\"Question\",\"id\":\"select_priority\",\"headline\":\"Select your top priority\",\"options\":[{\"id\":1,\"text\":\"Sleep\"},{\"id\":2,\"text\":\"Health\"}],\"submit\":\"Continue\"},{\"type\":\"Question\",\"id\":\"kids_age\",\"headline\":\"What best describe your kid’s age?\",\"options\":[{\"id\":1,\"text\":\"Newborn (0-3mo)\"},{\"id\":2,\"text\":\"Baby (3-12mo)\"},{\"id\":3,\"text\":\"Toddler (1-3 year)\"},{\"id\":4,\"text\":\"Preschool (3-5 year)\"},{\"id\":5,\"text\":\"School (5-11 year)\"},{\"id\":6,\"text\":\"Teen (11+ year)\"}]},{\"type\":\"TimeInput\",\"id\":\"child_nickname\",\"headline\":\"How do you call your child?\",\"textInputPlaceholder\":\"Name\",\"submit\":\"Continue\"},{\"type\":\"PromptWithDescriptionPoints\",\"id\":\"member_feedback\",\"headline\":\"Here’s what our members are saying:\",\"options\":[{\"id\":1,\"text\":\"87% Improved toddler sleep\"},{\"id\":2,\"text\":\"82% Reduced newborn sick time\"}],\"submitTop\":\"Based on a study of members who use Kidsy 5 times per week.\",\"submit\":\"Continue\"},{\"type\":\"QuestionMultiselect\",\"id\":\"sleep_problems\",\"headline\":\"What sleep problems are you facing?\",\"options\":[{\"id\":1,\"text\":\"Frequent wake-ups\"},{\"id\":2,\"text\":\"Time to fall asleep\"},{\"id\":3,\"text\":\"Early wakeups\"},{\"id\":4,\"text\":\"Chaotic nap schedule\"}],\"submit\":\"Continue\"},{\"type\":\"Question\",\"id\":\"time_to_sleep\",\"headline\":\"How long does it usually take to fall asleep?\",\"options\":[{\"id\":1,\"text\":\"5-15 minutes\"},{\"id\":2,\"text\":\"15-30 minutes\"},{\"id\":3,\"text\":\"30+ minutes\"}]},{\"type\":\"Question\",\"id\":\"toddler_naps\",\"headline\":\"How many naps your toddler have?\",\"options\":[{\"id\":1,\"text\":\"1\"},{\"id\":2,\"text\":\"2\"},{\"id\":3,\"text\":\"None\"}]},{\"type\":\"Question\",\"id\":\"parent_role\",\"headline\":\"What best describe your role?\",\"options\":[{\"id\":1,\"text\":\"Mom\"},{\"id\":2,\"text\":\"Dad\"},{\"id\":3,\"text\":\"Caregiver\"}]},{\"type\":\"Question\",\"id\":\"single_parent\",\"headline\":\"Are you a single parent?\",\"options\":[{\"id\":1,\"text\":\"Yes\"},{\"id\":2,\"text\":\"No\"}]},{\"type\":\"Prompt\",\"id\":\"kidsy_single_moms\",\"headlineTop\":\"Did you know?\",\"headline\":\"Kidsy helped 320,000 single moms improve their kids sleep.\",\"headlineBottom\":\"As of July 2024\",\"submit\":\"Continue\"},{\"type\":\"Question\",\"id\":\"learning_time\",\"headline\":\"When would you like to learn new parenting techniques?\",\"options\":[{\"id\":1,\"text\":\"Morning\"},{\"id\":2,\"text\":\"Afternoon\"},{\"id\":3,\"text\":\"Evening\"}]},{\"type\":\"AskForNotification\",\"id\":\"daily_reminder\",\"headline\":\"Get a daily reminder to meet your goals\",\"headlineBottom\":\"Reminders help you build better habits.\",\"submit\":\"Continue\"},{\"type\":\"TimeInput\",\"id\":\"reminder_time\",\"headline\":\"When would you like to be reminded?\",\"submit\":\"Set reminder\"}]}"
    }
}