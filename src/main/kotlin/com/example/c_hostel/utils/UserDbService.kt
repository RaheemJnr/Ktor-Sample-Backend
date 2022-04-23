package com.example.c_hostel.utils

import com.example.c_hostel.db.DB
import com.example.c_hostel.model.User
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

interface UserService {
    fun findAll(): List<User>
    fun find(id: Int): User?
    fun create(name: String, age: Int): EntityID<Int>
}

class UserServiceImpl : UserService {
    override fun findAll(): List<User> {
        return transaction {
            DB.UserTable.selectAll().map { row ->
                User(
                    row[DB.UserTable.id].value,
                    row[DB.UserTable.name],
                    row[DB.UserTable.age]
                )
            }
        }
    }

    override fun find(id: Int): User? {
        return transaction {
            val row = DB.UserTable.select {
                DB.UserTable.id.eq(id)
            }.firstOrNull()

            if (row != null) {
                User(
                    row[DB.UserTable.id].value,
                    row[DB.UserTable.name],
                    row[DB.UserTable.age]
                )
            } else {
                null
            }
        }
    }

    override fun create(name: String, age: Int): EntityID<Int> {
        return transaction {
            DB.UserTable.insertAndGetId { user ->
                user[DB.UserTable.name] = name
                user[DB.UserTable.age] = age
            }
        }
    }
}