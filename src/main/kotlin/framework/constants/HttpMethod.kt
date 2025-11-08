package framework.constants

enum class HttpMethod {
    GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS, CONNECT, TRACE;

    companion object {
        fun from(value: String): HttpMethod {
            return valueOf(value.uppercase())
        }
    }
}
