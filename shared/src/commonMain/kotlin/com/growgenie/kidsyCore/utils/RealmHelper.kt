package com.growgenie.kidsyCore.utils

import com.growgenie.kidsyCore.UserSession.CollectedOption
import com.growgenie.kidsyCore.UserSession.OnboardingScreenData
import com.growgenie.kidsyCore.UserSession.PlanData
import com.growgenie.kidsyCore.UserSession.UserSessionModel
import com.growgenie.kidsyCore.UserSession.UserSessionOption
import com.growgenie.kidsyCore.sleepTracker.SleepSession
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class RealmHelper {

    private val configuration: RealmConfiguration = RealmConfiguration.create(
        schema = setOf(
            UserSessionModel::class,
            OnboardingScreenData::class,
            PlanData::class,
            UserSessionOption::class,
            CollectedOption::class,
            SleepSession::class
        )
    )
    val realm: Realm = Realm.open(configuration)

    fun closeRealm() {
        realm.close()
    }

    fun resetRealm() {
        realm.close()
        Realm.deleteRealm(configuration)
    }
}