package cmndtools.dipumba.com.model

import kotlinx.serialization.Serializable

@Serializable
data class FeedbackData(
    val feedbackType: String,
    val satisfactionScore: Int,
    val npsScore: Int,
    val comment: String,
    val feedbackDate: String,
    val feedbackChannel: String,
    val feedbackCause: String,
    val feedbackImportance: String,
    val feedbackImpact: String,
    val resolutionStatus: String
)


