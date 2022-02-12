import com.example.c_hostel.db.DB
import com.example.c_hostel.mainModule
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.Assert.assertEquals
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*


internal class ServerTest {

    @Nested
    inner class `With user in DB` {

        // drop DB before conducting another test to avoid failed test
        @BeforeEach
        fun setUp() {
            DB.connect()
            transaction {
                SchemaUtils.drop(DB.UserTable)
            }
        }

        // test server status
        @Test
        fun testStatus() {
            withTestApplication(Application::mainModule) {
                val response = handleRequest(HttpMethod.Get, "/status").response
                assertEquals(
                    HttpStatusCode.OK,
                    response.status()
                )
                assertEquals(
                    """{"status": "OK"}""",
                    response.content
                )
            }
        }

        // mock post request
        @Test
        fun `POST creates a new user`() {
            withTestApplication(Application::mainModule) {
                val response = handleRequest(HttpMethod.Post, "/users") {
                    addHeader(
                        HttpHeaders.ContentType,
                        ContentType.Application.FormUrlEncoded.toString()
                    )
                    setBody(
                        listOf(
                            "name" to "Jnr",
                            "age" to 4.toString()
                        ).formUrlEncode()
                    )
                }.response
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }


    }

    //
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ServerTest {
        lateinit var id: EntityID<Int>

        // fetch user with id
        @Test
        fun `GET with ID to fetch a single user`() {
            withTestApplication(Application::mainModule) {
//                val id = transaction {
//                    DB.UserTable.insertAndGetId { cat ->
//                        cat[name] = "Jnr"
//                    }
//                }
                val response = handleRequest(HttpMethod.Get, uri = "/users/$id").response
                assertEquals("""{"id":$id,"name":"Jnr","age":0}""", response.content)
            }
        }

        //
        @Test
        fun `GET without ID fetches all users`() {
            withTestApplication(Application::mainModule) {
                val response = handleRequest(
                    HttpMethod.Get,
                    uri = "/users"
                ).response
                assertEquals("""[{"id":$id,"name":"Jnr","age":0}]""", response.content)
            }
        }


        @BeforeAll
        fun setup() {
            DB.connect()
            id = transaction {
                DB.UserTable.insertAndGetId { user ->
                    user[name] = "Jnr"
                }
            }
        }

        @AfterAll
        fun cleanUp() {
            DB.connect()
            transaction {
                DB.UserTable.deleteAll()
            }
        }
    }

}