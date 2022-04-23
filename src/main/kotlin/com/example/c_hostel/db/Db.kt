package com.example.c_hostel.db


import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database

object DB {
    private val host = System.getenv("DB_HOST") ?: "localhost"
    private val port =
        System.getenv("DB_PORT")?.toIntOrNull() ?: 5432
    private val dbName = System.getenv("DB_NAME") ?: "user_db"
    private val dbUser = System.getenv("DB_USER") ?: "postgres"
    private val dbPassword = System.getenv("DB_PASSWORD") ?: "80136930"

    //establish database connection
    fun connect() = Database.connect(
        url = "jdbc:postgresql://$host:$port/$dbName",
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword,
    )

    //create table
    object UserTable : IntIdTable() {
        val name = varchar(name = "name", length = 20).uniqueIndex()
        val age = integer(name = "age").default(defaultValue = 0)
    }


}

