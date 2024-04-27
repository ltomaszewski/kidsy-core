package com.growgenie.kidsyCore.model.screenState.screen

import com.growgenie.kidsyCore.model.screenState.ScreenName
import com.growgenie.kidsyCore.model.screenState.ScreenState
import com.growgenie.kidsyCore.model.screenState.UserAction

data class CreateAnAccountScreenState(val state: State = State.START): ScreenState {
    override val screenName: ScreenName = ScreenName.CREATE_AN_ACCOUNT

    enum class State {
        START, CREATING_VIA_SOCIAL, DONE
    }

    enum class Action: UserAction {
        SOCIAL_LOGIN, EMAIL
    }

    enum class SocialLoginAction {
        APPLE, GOOGLE, FACEBOOK, EMAIL
    }

    val appleLogin = "Continue with Apple"
    val googleLogin = "Continue with Google"
    val facebookLogin = "Continue with Facebook"
    val emailLogin = "Continue with Email"
    val bottomDescription = "By creating your account, you agree to Kidsy’s Terms & Conditions and Privacy Policy."
}