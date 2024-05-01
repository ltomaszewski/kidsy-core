import kotlinx.serialization.Serializable

@Serializable
data class OnboardingModel(
    val screens: List<OnboardingScreenModel>
)

@Serializable
data class OnboardingScreenModel(
    val type: OnboardingScreenType,
    val id: String,
    val headline: String,
    val headlineTop: String? = null,
    val headlineBottom: String? = null,
    val headlineBottomImageName: String? = null,
    val options: List<OnboardingOption> = emptyList(),
    val submitTop: String? = null,
    val submit: String? = null,
    val textInputPlaceholder: String? = null
)

@Serializable
data class OnboardingOption(
    val id: Int,
    val text: String
)

@Serializable
enum class OnboardingScreenType {
    Question, // No submit button; no need, single select
    QuestionMultiselect, // Submit button enabled only if any option was selected.
    TextInput, // Screen to submit short text
    TimeInput, // Screen to pick time
    Prompt, // If submit button is not present, the next screen appears on tap anywhere. SubmitTopText should have a description.
    PromptWithDescriptionPoints,
    AskForNotification,
}