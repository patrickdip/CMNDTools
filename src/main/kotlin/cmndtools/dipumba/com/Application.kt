package cmndtools.dipumba.com

import cmndtools.dipumba.com.dao.DatabaseFactory
import cmndtools.dipumba.com.di.configureDI
import cmndtools.dipumba.com.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureDI()
    configureRouting()
}
