package com.growgenie.kidsyCore.model.screenState.screen

import kotlinx.serialization.Serializable

@Serializable
data class ScreenModel(
    val type: ScreenType,
    val id: String,
    val navigationBarTitle: String? = null,
    val headline: String,
    val headlineImageName: String? = null,
    val headlineTop: String? = null,
    val headlineBottom: String? = null,
    val headlineBottomImageName: String? = null,
    val options: List<Option> = emptyList(),
    val submitTop: String? = null,
    val submit: String? = null,
    val textInputPlaceholder: String? = null
)

@Serializable
data class Option(
    val id: Int,
    val text: String,
    val imageName: String? = null
)

@Serializable
enum class ScreenType {
    Question, // No submit button; no need, single select
    QuestionMultiselect, // Submit button enabled only if any option was selected.
    TextInput, // Screen to submit short text
    TimeInput, // Screen to pick time
    Prompt, // If submit button is not present, the next screen appears on tap anywhere. SubmitTopText should have a description.
    PromptWithDescriptionPoints,
    AskForNotification,
}