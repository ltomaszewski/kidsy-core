package com.growgenie.kidsyCore.model.screenState.screen.plan

import com.growgenie.kidsyCore.model.screenState.ScreenName
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.UserAction
import com.growgenie.kidsyCore.model.screenState.screen.Option
import com.growgenie.kidsyCore.model.screenState.screen.ScreenModel
import com.growgenie.kidsyCore.model.screenState.screen.onboarding.OnboardingScreenState
import kotlinx.serialization.json.Json

data class PlanScreenState(val jsonInput: String = Plans.day0,
                           val state: State = State.START,
                           val index: Int = 0,
                           val selectedOptions: List<Option> = listOf()): ScreenState {

    override val screenName: ScreenName = ScreenName.PLAN
    enum class State { START, PLAN, DONE }

    enum class ActionType {
        SUBMIT, SELECT, TEXT_INPUT
    }

    class Action(val type: ActionType, val option: Int? = null, val text: String? = null) :
        UserAction

    val planModel: PlanModel
    val currentScreen: ScreenModel
    val size : Int

    init {
        val json = Json { ignoreUnknownKeys = true } // Configure the Json to ignore unknown keys
        planModel = json.decodeFromString(PlanModel.serializer(), jsonInput)
        currentScreen = planModel.screens[index]
        size = planModel.screens.size
    }

    // Method to create a new state with increased index
    fun nextScreen(): PlanScreenState {
        return if (index < planModel.screens.size - 1) {
            if (state == State.START) {
                copy(index = index + 1, state = State.PLAN)
            } else {
                copy(index = index + 1)
            }
        } else {
            copy(state = State.DONE)
        }
    }

    fun updateWithSelectedOption(optionId: Int): PlanScreenState {
        val foundOption = currentScreen.options.firstOrNull { it.id == optionId }

        if (foundOption != null) {
            val newSelectedOptions = selectedOptions.toMutableList()
            if (!newSelectedOptions.contains(foundOption)) {
                newSelectedOptions.add(foundOption)
            }

            return copy(selectedOptions = newSelectedOptions)
        } else {
            throw IllegalArgumentException("Option with ID $optionId not found.")
        }
    } }