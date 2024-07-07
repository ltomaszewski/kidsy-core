package com.growgenie.kidsyCore.UserSession

import com.growgenie.kidsyCore.utils.RealmHelper
import io.realm.kotlin.ext.query

class UserSession(private val realmHelper: RealmHelper) {

    private val realm = realmHelper.realm
    val hasFinishedOnboarding: Boolean
        get() = userSessionModel.userIdentifier.isNotEmpty()

    val userSessionModel: UserSessionModel
        get() {
            val session = realm.query<UserSessionModel>("id == $0", "session").find().firstOrNull()
            return session ?: UserSessionModel().apply { id = "session" }
        }

    init {
        println("UserSession initialized")
    }

    private fun updateSession(block: (userSessionModel: UserSessionModel) -> Unit) {
        println("Executing UpdateSession: Update user session to database")
        realm.writeBlocking {
            var session = query<UserSessionModel>("id == $0", "session").find().firstOrNull()
            if (session == null) {
                val newSession = UserSessionModel().apply { id = "session" }
                copyToRealm(newSession)
                session = query<UserSessionModel>("id == $0", "session").find().first()
            }
            println("Current User Session: $session")
            block(session)
        }
    }

    fun setUserInfo(userIdentifier: String, fullName: String, email: String) {
        println("Executing setUserInfo: Setting user info with identifier $userIdentifier")
        updateSession {
            it.userIdentifier = userIdentifier
            it.fullName = fullName
            it.email = email
        }
    }

    fun addOrUpdateOnboardingData(screenId: String, option: UserSessionOption) {
        println("Executing addOrUpdateOnboardingData: Adding or updating onboarding data for screen $screenId")
        updateSession {
            val existingScreenData =
                it.onboardingData.find { it.screenId == screenId }
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
                it.onboardingData.add(newScreenData)
            }
        }
    }

//    fun getOnboardingData(screenId: String): List<UserSessionOption> {
//        println("Executing getOnboardingData: Retrieving onboarding data for screen $screenId")
//        return userSessionModel.onboardingData.find { it.screenId == screenId }?.options
//            ?: emptyList()
//    }

    fun addOrUpdatePlan(planId: String) {
        println("Executing addOrUpdatePlan: Adding or updating plan with id $planId")
        updateSession {
            val existingPlan = it.plans.find { it2 -> it2.planId == planId }
            if (existingPlan != null) {
                existingPlan.planId = planId
            } else {
                val newPlan = PlanData().apply {
                    this.planId = planId
                }
                it.plans.add(newPlan)
            }
        }
    }

    fun updateCollectedData(planId: String, screenId: String, option: UserSessionOption) {
        println("Executing updateCollectedData: Updating collected data for plan $planId and screen $screenId")
        updateSession {
            val plan = it.plans.find { it2 -> it2.planId == planId }
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
            }
        }
    }

//    fun getCollectedData(planId: String, screenId: String): UserSessionOption? {
//        println("Executing getCollectedData: Retrieving collected data for plan $planId and screen $screenId")
//        return userSessionModel.plans.find { it.planId == planId }?.collectedData?.find { it.screenId == screenId }?.option
//    }
}
