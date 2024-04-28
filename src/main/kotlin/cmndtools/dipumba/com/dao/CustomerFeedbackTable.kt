package cmndtools.dipumba.com.dao

import cmndtools.dipumba.com.model.FeedbackData
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object CustomerFeedbackTable : Table(name = "customer_feedback") {
    val feedbackId = long(name = "feedback_id").uniqueIndex()
    val feedbackType = varchar(name = "feedback_type", length = 50) // Complaint, Suggestion, Compliment, Inquiry
    val satisfactionScore = integer(name = "satisfaction_score")
    val npsScore = integer(name = "nps_score")
    val comment = text(name = "comment")
    val feedbackDate = text(name = "feedback_date")
    val feedbackChannel = varchar(name = "feedback_channel", length = 50)
    val feedbackCause = varchar(name = "feedback_cause", length = 100)
    val feedbackImportance = varchar(name = "feedback_importance", length = 50)
    val feedbackImpact = varchar(name = "feedback_impact", length = 50)
    val location = varchar(name = "location", length = 500)
    val resolutionStatus = varchar(name = "resolution_status", length = 20) // ADDRESSED, PENDING, CLOSED

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(feedbackId)
}

@Serializable
data class CustomerFeedbackRow(
    val feedbackId: Long,
    val feedbackType: String,
    val satisfactionScore: Int,
    val npsScore: Int,
    val comment: String,
    val feedbackDate: String,
    val feedbackChannel: String,
    val feedbackCause: String,
    val feedbackImportance: String,
    val feedbackImpact: String,
    val location: String,
    val resolutionStatus: String
){
    fun toFeedbackData(): FeedbackData{
        return FeedbackData(
            feedbackType,
            satisfactionScore,
            npsScore,
            comment,
            feedbackDate,
            feedbackChannel,
            feedbackCause,
            feedbackImportance,
            feedbackImpact,
            resolutionStatus,
        )
    }
}