package framework.exception

import framework.dto.Response
import kotlin.Exception

class ExceptionResponseHandler {
    fun createErrorResponse(exception: Exception): Response {
        return Response(
            code = exception.statusCode,
            contentType = "text/plain",
            body = exception.stackTraceToString()
        )
    }

    private val Exception.statusCode: Int
        get() {
            return when (this) {
                is ResponsibleException -> code
                else -> 500
            }
        }
}
