package cmndtools.dipumba.com.repository

import cmndtools.dipumba.com.dao.CustomerIssuesDao
import cmndtools.dipumba.com.dao.CustomerFeedbackRow
import cmndtools.dipumba.com.model.FeedbackData

class CustomerIssuesRepository (
    private val dao: CustomerIssuesDao
){
    suspend fun insertIssue(issueData: FeedbackData): Boolean{
        return dao.insert(issueData = issueData)
    }

    suspend fun insertAllIssues(issues: List<FeedbackData>): Boolean{
        return dao.insertAll(issues = issues)
    }

    suspend fun getAllIssues(): List<CustomerFeedbackRow>{
        return dao.getIssues()
    }
}