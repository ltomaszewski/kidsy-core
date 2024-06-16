package com.growgenie.kidsyCore.UserSession

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

open class UserSessionModel : RealmObject {
    @PrimaryKey
    var id: String = "session"
    var onboardingData: RealmList<OnboardingScreenData> = realmListOf()
    var plans: RealmList<PlanData> = realmListOf()
    var userIdentifier: String = ""
    var fullName: String = ""
    var email: String = ""

    override fun toString(): String {
        return "UserSessionModel(id='$id', userIdentifier='$userIdentifier', fullName='$fullName', email='$email', onboardingData=$onboardingData, plans=$plans)"
    }
}

open class OnboardingScreenData : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var screenId: String = ""
    var options: RealmList<UserSessionOption> = realmListOf()

    override fun toString(): String {
        return "OnboardingScreenData(_id=$_id, screenId='$screenId', options=$options)"
    }
}

open class PlanData : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var planId: String? = null
    var collectedData: RealmList<CollectedOption> = realmListOf()

    override fun toString(): String {
        return "PlanData(_id=$_id, planId=$planId, collectedData=$collectedData)"
    }
}

open class CollectedOption : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var screenId: String = ""
    var option: UserSessionOption? = null

    override fun toString(): String {
        return "CollectedOption(_id=$_id, screenId='$screenId', option=$option)"
    }
}

open class UserSessionOption : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var jsonId: String = ""
    var text: String = ""

    override fun toString(): String {
        return "UserSessionOption(_id=$_id, jsonId='$jsonId', text='$text')"
    }
}