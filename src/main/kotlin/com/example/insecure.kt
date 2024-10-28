import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import kotlin.random.Random

fun sqlInjectionExample(username: String, password: String) {
    val connection: Connection =
        DriverManager.getConnection("jdbc:postgresql://localhost:5432/testdb", "user", "password")
    // Vulnerable to SQL injection
    val query = "SELECT * FROM users WHERE username = '$username' AND password = '$password'"
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(query)

    if (resultSet.next()) {
        println("User authenticated: ${resultSet.getString("username")}")
    } else {
        println("Invalid credentials")
    }
    connection.close()
}

fun insecureDataStorageExample() {
    val password = "SuperSecretPassword" // Sensitive data stored in plaintext
    println("User's password is: $password") // Exposed sensitive data
}

fun insecureLibraryUseExample() {
    // Using an outdated or vulnerable version of a library
    // Suppose an external library has a known vulnerability
    val result = vulnerableLibraryFunction() // This function may have security issues
    println("Result from insecure library: $result")
}

fun xssExample(userInput: String) {
    // Vulnerable to XSS
    println("User input: <h1>$userInput</h1>") // Outputs raw HTML without escaping
}

fun insecureRandomnessExample() {
    val randomValue = Random.nextInt(100) // Not secure for cryptographic use
    println("Random value: $randomValue") // Vulnerable to prediction
}

fun inadequateExceptionHandlingExample() {
    try {
        // Simulate an operation that may throw an exception
        val result = riskyOperation()
        println("Operation successful: $result")
    } catch (e: Exception) {
        println("An error occurred: ${e.message}") // May expose sensitive information
    }
}

fun directoryTraversalExample(filePath: String) {
    // Vulnerable to directory traversal
    val file = File("/user/data/$filePath")
    if (file.exists()) {
        println("File content: ${file.readText()}")
    } else {
        println("File not found")
    }
}

fun hardcodedCredentialsExample() {
    val username = "admin"
    val password = "password123" // Hardcoded credentials
    println("Logging in with username: $username and password: $password") // Exposed credentials
}

fun sessionFixationExample(sessionId: String) {
    // Accepting user-provided session ID
    val currentSessionId = sessionId // Vulnerable to session fixation
    println("Current session ID: $currentSessionId")
}

fun insecureFileUploadExample(fileName: String) {
    // No validation for file type or content
    val uploadedFile = File("/uploads/$fileName")
    if (uploadedFile.exists()) {
        println("File uploaded: ${uploadedFile.name}")
    } else {
        println("Upload failed.")
    }
}
