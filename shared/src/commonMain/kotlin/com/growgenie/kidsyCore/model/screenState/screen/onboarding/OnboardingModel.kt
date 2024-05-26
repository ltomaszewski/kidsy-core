import com.growgenie.kidsyCore.model.screenState.screen.ScreenModel
import kotlinx.serialization.Serializable

@Serializable
data class OnboardingModel(
    val screens: List<ScreenModel>
)