import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.html.*
import io.ktor.http.ContentType
import io.ktor.request.receiveParameters
import io.ktor.response.respondText
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.mindrot.jbcrypt.BCrypt
import java.security.MessageDigest
import java.sql.Connection
import java.sql.DriverManager

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) // Install ContentNegotiation feature if needed
        routing {
            post("/login") {
                val parameters = call.receiveParameters()
                val username = parameters["username"] ?: ""
                val password = parameters["password"] ?: ""

                val user = authenticateUser(username, password)
                if (user != null) {
                    call.respondText("Welcome $username!")
                } else {
                    call.respondText("Invalid credentials")
                }
            }

            get("/greet") {
                val name = call.parameters["name"] ?: "Guest"
                call.respondText("<h1>Hello, $name</h1>", ContentType.Text.Html)
            }
        }
    }.start(wait = true)
}

fun authenticateUser(
    username: String,
    password: String,
): String? {
    // Establish a connection to the database
    val connection: Connection? = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testdb", "user", "password")

    return connection?.use { conn -> // Use 'use' to ensure the connection is closed
        // Use prepared statements to avoid SQL injection
        val query = "SELECT * FROM users WHERE username = ?"
        val preparedStatement = conn.prepareStatement(query).apply {
            setString(1, username)
        }

        val resultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            val storedPassword = resultSet.getString("password")
            // Compare hashed passwords
            if (BCrypt.checkpw(password, storedPassword)) {
                resultSet.getString("username")
            } else {
                null
            }
        } else {
            null
        }
    }
}

fun hashPassword(password: String): String {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(password.toByteArray())
    return digest.joinToString("") { "%02x".format(it) }
}

fun secureHashPassword(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())