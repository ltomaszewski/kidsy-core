package com.growgenie.kidsyCore.UserSession

import com.growgenie.kidsyCore.utils.RealmHelper
import io.realm.kotlin.ext.query

class UserSession(private val realmHelper: RealmHelper) {

    private val realm = realmHelper.realm
    val userSessionModel: UserSessionModel
    val hasFinishedOnboarding: Boolean
        get() = userSessionModel.userIdentifier.isNotEmpty()

    init {
        println("UserSession initialized")
        userSessionModel = loadSession()
        saveSession()
    }

    private fun loadSession(): UserSessionModel {
        println("Executing loadSession: Loading user session from database")
        realm.writeBlocking {
            delete(this.query<UserSessionModel>().find())
        }

        val session = realm.query<UserSessionModel>("id == $0", "session")
        return if (session is UserSessionModel) {
            session
        } else {
            UserSessionModel()
        }
    }

    private fun saveSession() {
        println("Executing saveSession: Saving user session to database")
        println("Current User Session: $userSessionModel")
        realm.writeBlocking {
//            copyToRealm(userSessionModel)
        }
    }

    fun setUserInfo(userIdentifier: String, fullName: String, email: String) {
        println("Executing setUserInfo: Setting user info with identifier $userIdentifier")
        userSessionModel.userIdentifier = userIdentifier
        userSessionModel.fullName = fullName
        userSessionModel.email = email
        saveSession()
    }

    fun addOrUpdateOnboardingData(screenId: String, option: UserSessionOption) {
        println("Executing addOrUpdateOnboardingData: Adding or updating onboarding data for screen $screenId")
        val existingScreenData = userSessionModel.onboardingData.find { it.screenId == screenId }
        if (existingScreenData != null) {
            val existingOption =
                existingScreenData.options.find { it.jsonId == option.jsonId }
            if (existingOption != null) {
                existingOption.apply {
                    this.text = option.text
                }
            } else {
                existingScreenData.options.add(option)
            }
        } else {
            val newScreenData = OnboardingScreenData().apply {
                this.screenId = screenId
                this.options.add(option)
            }
            userSessionModel.onboardingData.add(newScreenData)
        }
        saveSession()
    }

    fun getOnboardingData(screenId: String): List<UserSessionOption> {
        println("Executing getOnboardingData: Retrieving onboarding data for screen $screenId")
        return userSessionModel.onboardingData.find { it.screenId == screenId }?.options
            ?: emptyList()
    }

    fun addOrUpdatePlan(planId: String) {
        println("Executing addOrUpdatePlan: Adding or updating plan with id $planId")
        val existingPlan = userSessionModel.plans.find { it.planId == planId }
        if (existingPlan != null) {
            existingPlan.planId = planId
        } else {
            val newPlan = PlanData().apply {
                this.planId = planId
            }
            userSessionModel.plans.add(newPlan)
        }
        saveSession()
    }

    fun updateCollectedData(planId: String, screenId: String, option: UserSessionOption) {
        println("Executing updateCollectedData: Updating collected data for plan $planId and screen $screenId")
        val plan = userSessionModel.plans.find { it.planId == planId }
        if (plan != null) {
            val existingOption = plan.collectedData.find { it.screenId == screenId }
            if (existingOption != null) {
                existingOption.option = option
            } else {
                val newCollectedOption = CollectedOption().apply {
                    this.screenId = screenId
                    this.option = option
                }
                plan.collectedData.add(newCollectedOption)
            }
            saveSession()
        }
    }

    fun getCollectedData(planId: String, screenId: String): UserSessionOption? {
        println("Executing getCollectedData: Retrieving collected data for plan $planId and screen $screenId")
        return userSessionModel.plans.find { it.planId == planId }?.collectedData?.find { it.screenId == screenId }?.option
    }
}
