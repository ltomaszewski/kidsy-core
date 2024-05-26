package com.growgenie.kidsyCore.model.screenState.screen.plan

import com.growgenie.kidsyCore.model.screenState.screen.ScreenModel
import kotlinx.serialization.Serializable

@Serializable
data class PlanModel(
    val navigationBarTitle: String? = null,
    val headlineImageName: String = "moon_purpule",
    val headline: String,
    val headlineBottom: String,
    val submit: String,
    val screens: List<ScreenModel>
    )