package com.example.c_hostel

import com.example.c_hostel.db.DB
import com.example.c_hostel.routes.userRoute
import com.example.c_hostel.utils.UserServiceImpl
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

fun Application.mainModule() {
    // create Db
    DB.connect()
    transaction {
        SchemaUtils.create(DB.UserTable)
    }
    // plugin for content negotiation
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    val service = UserServiceImpl()
    userRoute(service = service)
    //routing
    routing {
        // status
        get("/status") {
            call.respond(mapOf("status" to "OK"))
        }
    }
}