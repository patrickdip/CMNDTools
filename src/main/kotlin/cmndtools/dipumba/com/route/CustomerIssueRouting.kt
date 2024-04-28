package cmndtools.dipumba.com.route

import cmndtools.dipumba.com.model.DovecListing
import cmndtools.dipumba.com.model.FeedbackData
import cmndtools.dipumba.com.model.RemoteDovecListing
import cmndtools.dipumba.com.repository.CustomerIssuesRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Routing.customerIssueRouting(){
    val repository by inject<CustomerIssuesRepository>()

    val client = HttpClient(CIO)
    val json = Json { ignoreUnknownKeys = true }

    route(path = "/feedback-data"){
        post {
            val issueData = call.receiveNullable<Array<FeedbackData>>()
            if (issueData == null){
                call.respond(status = HttpStatusCode.BadRequest, message = "No data passed")
                return@post
            }

            try {
                issueData.forEach { issueData ->
                    repository.insertIssue(issueData = issueData )
                }
                call.respond(
                    status = HttpStatusCode.OK,
                    message = "Great Inserted"
                )
            }catch (error: Throwable){
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = "Unexpected Error ${error.message}"
                )
            }
        }
    }

    route(path = "/dovec-listings"){
        get {
            try {
                val response: HttpResponse = client.get("https://api.npoint.io/2c308c84cfe292772cdf/items/item/")
                val jsonString = response.bodyAsText()
                val data = json.decodeFromString<Array<RemoteDovecListing>>(jsonString)

                val result = repository.getAllIssues()
                call.respond(
                    status = HttpStatusCode.OK,
                    message = generateResponse(
                        feedbackDataList = result.map { it.toFeedbackData() },
                        dovecListingList = data.toList().map { it.toDovecListing() }
                    )
                )
            }catch (exception: IllegalArgumentException){
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = "Illegal Argument ${exception.message}"
                )
            }catch (error: Throwable){
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = "Unexpected Error ${error.message}"
                )
            }
        }
    }
}

private fun generateResponse(feedbackDataList: List<FeedbackData>, dovecListingList: List<DovecListing>): List<DovecListing> {
    val selectedListings = dovecListingList.shuffled().take(25)
    val listingsSet = mutableSetOf<DovecListing>()
    selectedListings.forEach { listing ->
        when (listing.type) {
            "For Sale", "Flat" -> {
                listing.status = "SOLD"
                listing.customerFeedback = feedbackDataList.random()
            }
            "For Rent" -> {
                listing.status = "RENTED"
                listing.customerFeedback = feedbackDataList.random()
            }
            else -> {
                listing.status = "AVAILABLE"
                listing.customerFeedback = null
            }
        }
        listingsSet.add(listing)
    }
    listingsSet.addAll(dovecListingList)
    return listingsSet.toList().shuffled()
}

