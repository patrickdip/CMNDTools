package cmndtools.dipumba.com.plugins

import cmndtools.dipumba.com.route.customerIssueRouting
import io.ktor.client.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Application.configureRouting() {
    routing {
        customerIssueRouting()
    }
}
