package cmndtools.dipumba.com.dao

import cmndtools.dipumba.com.dao.DatabaseFactory.dbQuery
import cmndtools.dipumba.com.util.IdGenerator
import cmndtools.dipumba.com.model.FeedbackData
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.BatchInsertStatement

class CustomerIssuesDaoImpl : CustomerIssuesDao {
    override suspend fun insert(feedbackData: FeedbackData): Boolean {
        return dbQuery {
            val insertStatement = CustomerFeedbackTable.insert {
                it[feedbackId] = IdGenerator.generateId()
                it[feedbackType] = feedbackData.feedbackType
                it[satisfactionScore] = feedbackData.satisfactionScore
                it[npsScore] = feedbackData.npsScore
                it[comment] = feedbackData.comment
                it[feedbackDate] = feedbackData.feedbackDate
                it[feedbackChannel] = feedbackData.feedbackChannel
                it[feedbackCause] = feedbackData.feedbackCause
                it[feedbackImportance] = feedbackData.feedbackImportance
                it[feedbackImpact] = feedbackData.feedbackImpact
                it[location] = ""
                it[resolutionStatus] = feedbackData.resolutionStatus
            }
            insertStatement.resultedValues?.singleOrNull() != null
        }
    }

    override suspend fun insertAll(issues: List<FeedbackData>): Boolean {
        return dbQuery {
            try {
                BatchInsertStatement(CustomerFeedbackTable).run {
                    issues.forEach { feedbackData ->
                        this[CustomerFeedbackTable.feedbackId] = IdGenerator.generateId()
                        this[CustomerFeedbackTable.feedbackType] = feedbackData.feedbackType
                        this[CustomerFeedbackTable.satisfactionScore] = feedbackData.satisfactionScore
                        this[CustomerFeedbackTable.npsScore] = feedbackData.npsScore
                        this[CustomerFeedbackTable.comment] = feedbackData.comment
                        this[CustomerFeedbackTable.feedbackDate] = feedbackData.feedbackDate
                        this[CustomerFeedbackTable.feedbackChannel] = feedbackData.feedbackChannel
                        this[CustomerFeedbackTable.feedbackCause] = feedbackData.feedbackCause
                        this[CustomerFeedbackTable.feedbackImportance] = feedbackData.feedbackImportance
                        this[CustomerFeedbackTable.feedbackImpact] = feedbackData.feedbackImpact
                        this[CustomerFeedbackTable.location] = ""
                        this[CustomerFeedbackTable.resolutionStatus] = feedbackData.resolutionStatus
                        addBatch()
                    }
                }
                true
            } catch (error: Throwable) {
                false
            }
        }
    }

    override suspend fun getIssues(): List<CustomerFeedbackRow> {
        return dbQuery {
            CustomerFeedbackTable.selectAll().limit(n = 25).map { toRow(it) }
        }
    }

    private fun toRow(row: ResultRow): CustomerFeedbackRow {
        return CustomerFeedbackRow(
            feedbackId = row[CustomerFeedbackTable.feedbackId],
            feedbackType = row[CustomerFeedbackTable.feedbackType],
            satisfactionScore = row[CustomerFeedbackTable.satisfactionScore],
            npsScore = row[CustomerFeedbackTable.npsScore],
            comment = row[CustomerFeedbackTable.comment],
            feedbackDate = row[CustomerFeedbackTable.feedbackDate],
            feedbackChannel = row[CustomerFeedbackTable.feedbackChannel],
            feedbackCause = row[CustomerFeedbackTable.feedbackCause],
            feedbackImportance = row[CustomerFeedbackTable.feedbackImportance],
            feedbackImpact = row[CustomerFeedbackTable.feedbackImpact],
            location = row[CustomerFeedbackTable.location],
            resolutionStatus = row[CustomerFeedbackTable.resolutionStatus],
        )
    }
}