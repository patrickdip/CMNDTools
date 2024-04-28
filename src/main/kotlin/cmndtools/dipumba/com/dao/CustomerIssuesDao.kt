package cmndtools.dipumba.com.dao

import cmndtools.dipumba.com.model.FeedbackData

interface CustomerIssuesDao {
    suspend fun insert(issueData: FeedbackData): Boolean

    suspend fun insertAll(issues: List<FeedbackData>): Boolean

    suspend fun getIssues(): List<CustomerFeedbackRow>
}