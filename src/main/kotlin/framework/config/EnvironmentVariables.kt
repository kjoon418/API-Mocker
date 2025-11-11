package framework.config

val BASE_PACKAGES = listOf("framework", "mocker")
const val CHARACTER_ENCODING = "UTF-8"
const val SERVER_PORT = 8080
const val SERVER_BASE_DIR_PATH = "tomcat-temp"
const val SERVLET_NAME = "ApiServlet"

const val JWT_SECRET = "watson-jwt-secret-key"
const val JWT_ISSUER = "watson-api"
const val JWT_EXPIRED = 360000L
