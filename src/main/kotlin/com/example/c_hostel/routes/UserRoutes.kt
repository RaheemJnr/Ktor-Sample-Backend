package com.example.c_hostel.routes

import com.example.c_hostel.utils.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.userRoute(service: UserService) {
    routing {
        getSingleUser(service)
        inputNewUser(service)
        listAllUsers(service)
    }
}

//// get a single user
fun Route.getSingleUser(service: UserService) {
    get("/users/{id}") {
        val id = call.parameters["id"]?.toInt() ?: return@get call.respondText(
            text = "Missing or malformed id",
            status = HttpStatusCode.BadRequest
        )
        val user = service.find(id)
        if (user == null) {
            call.respond(HttpStatusCode.NotFound)
            call.respondText(text = "User not Found!!")
        } else {
            call.respond(user)
        }
//        val user = transaction {
//            DB.UserTable.select {
//                DB.UserTable.id.eq(id)
//            }.firstOrNull()
//        }
//        if (user == null) {
//            call.respond(HttpStatusCode.NotFound)
//        } else {
//            call.respond(
//                User(
//                    id = user[DB.UserTable.id].value,
//                    name = user[DB.UserTable.name],
//                    age = user[DB.UserTable.age]
//                )
//            )
//        }

    }
}

//// create user
fun Route.inputNewUser(service: UserService) {
    post("/Users") {
        call.respond(HttpStatusCode.Created)
        val parameter = call.receiveParameters()
        val name = requireNotNull(parameter["name"])
        val age = parameter["age"]?.toInt() ?: 0
        //
        service.create(name = name, age = age)
//        transaction {
//            DB.UserTable.insert { user ->
//                user[DB.UserTable.name] = name
//                user[DB.UserTable.age] = age
//            }
//        }
    }
}

//// get all user
fun Route.listAllUsers(service: UserService) {
    get("/users") {
        val user = service.findAll()
        call.respond(user)
//        val users = transaction {
//            DB.UserTable.selectAll().map { resultRow ->
//                User(
//                    id = resultRow[DB.UserTable.id].value,
//                    name = resultRow[DB.UserTable.name],
//                    age = resultRow[DB.UserTable.age],
//                )
//            }
//        }
//        call.respond(users)
    }
}