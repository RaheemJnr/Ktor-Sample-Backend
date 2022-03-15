package com.example.c_hostel

import com.example.c_hostel.db.DB
import com.example.c_hostel.routes.userRoute
import com.example.c_hostel.utils.UserServiceImpl
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
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
    //routing
    routing {
        // status
        get("/status") {
            call.respond(mapOf("status" to "OK"))
        }
        val service = UserServiceImpl()
        // user endpoint route
        userRoute(service = service)
    }



}