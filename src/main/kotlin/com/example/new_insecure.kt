import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.URL
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.net.ssl.HttpsURLConnection

fun insufficientLoggingExample() {
    // Logging failed login attempts but not successful ones
    val username = "user123"
    val loginSuccessful = false // Simulate a failed login

    if (!loginSuccessful) {
        println("Failed login attempt for user: $username") // Log only failures
    }
    // No logs for successful logins, which can help in monitoring
}

fun deprecatedAlgorithmExample(data: String) {
    // Using an outdated algorithm (e.g., DES)
    val key = SecretKeySpec("12345678".toByteArray(), "DES")
    val cipher = Cipher.getInstance("DES/ECB/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, key)
    val encryptedData = cipher.doFinal(data.toByteArray())
    println("Encrypted data: ${encryptedData.joinToString(", ")}")
}

fun improperCertificateValidationExample() {
    val url = URL("https://insecure-website.com")
    val conn = url.openConnection() as HttpsURLConnection

    // Remove or properly implement SSL certificate validation
    // For demonstration, we won't change the default behavior
    val responseCode = conn.responseCode
    println("Response Code: $responseCode")
}

fun weakPasswordPolicyExample(password: String) {
    if (password.length < 6) {
        println("Password is too weak") // Insufficient password strength requirements
    } else {
        println("Password accepted")
    }
}

fun unvalidatedRedirectExample(routing: Routing) {
    routing.get("/redirect") {
        val targetUrl = call.parameters["url"] ?: "http://default.com"
        // Redirecting without validation
        call.respondRedirect(targetUrl) // Potential open redirect vulnerability
    }
}

fun commandInjectionExample(command: String) {
    // Vulnerable to command injection
    val process = Runtime.getRuntime().exec(command)
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    val output = reader.readText()
    println("Command output: $output")
}

fun csrfVulnerabilityExample() {
    // Assume there's a form submission that doesn't validate CSRF tokens
    // If a user submits a form without a CSRF token validation
    println("Form submitted without CSRF protection")
}

fun insecureApiExposureExample() {
    // An API endpoint that doesn't require authentication
    println("Accessing sensitive data without authentication")
    // Should implement proper authentication mechanisms
}

fun resourceExhaustionExample(data: String) {
    // Accepting large input without validation
    val buffer = StringBuilder()
    for (i in 1..1000000) {
        buffer.append(data)
    }
    println("Data processed: ${buffer.length} characters")
}

fun pathTraversalExample(fileName: String) {
    // Accepting user input directly into file path
    val file = File("/uploads/$fileName")
    if (file.exists()) {
        println("File content: ${file.readText()}")
    } else {
        println("File not found")
    }
}
