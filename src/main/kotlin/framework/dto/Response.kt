package framework.dto

data class Response(
    val code: Int,
    val contentType: String,
    val body: String
)
