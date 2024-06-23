package com.growgenie.kidsyCore.stateHandler.PlanScreenStateStateHandler

import com.growgenie.kidsyCore.UserSession.UserSessionModel
import com.growgenie.kidsyCore.model.screenState.screen.ScreenModel

class PlaceholderReplacer {

    fun fillPlaceholders(screenModel: ScreenModel, userSession: UserSessionModel): ScreenModel {
        val placeholders = mapOf(
            "{childNickname}" to findPlaceholderValue("childNickname", userSession),
            "{user_name}" to userSession.fullName
        )

        return screenModel.copy(
            navigationBarTitle = screenModel.navigationBarTitle?.replacePlaceholders(placeholders),
            headline = screenModel.headline.replacePlaceholders(placeholders),
            headlineTop = screenModel.headlineTop?.replacePlaceholders(placeholders),
            headlineBottom = screenModel.headlineBottom?.replacePlaceholders(placeholders),
            submitTop = screenModel.submitTop?.replacePlaceholders(placeholders),
            submit = screenModel.submit?.replacePlaceholders(placeholders),
            textInputPlaceholder = screenModel.textInputPlaceholder?.replacePlaceholders(
                placeholders
            )
        )
    }

    private fun findPlaceholderValue(placeholder: String, userSession: UserSessionModel): String {
        userSession.onboardingData.forEach { onboardingData ->
            onboardingData.options.forEach { option ->
                if (option.jsonId == placeholder) {
                    return option.text
                }
            }
        }
        return ""
    }

    private fun String.replacePlaceholders(placeholders: Map<String, String>): String {
        var result = this
        placeholders.forEach { (key, value) ->
            result = result.replace(key, value)
        }
        return result
    }
}