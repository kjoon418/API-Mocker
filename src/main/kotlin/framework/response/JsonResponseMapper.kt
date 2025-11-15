package framework.response

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import framework.action.ActionResponse

class JsonResponseMapper(
    private val objectMapper: ObjectMapper = jacksonObjectMapper()
) : ResponseMapper {
    override fun map(actionResponse: ActionResponse<*>): Response {
        val jsonBody = actionResponse.body.serialize()
        val contentType = CONTENT_TYPE_JSON

        return Response(
            status = actionResponse.status,
            contentType = contentType,
            body = jsonBody
        )
    }

    private fun Any?.serialize(): String {
        return when (this) {
            null -> EMPTY_JSON
            is String -> this
            else -> objectMapper.writeValueAsString(this)
        }
    }

    companion object {
        private const val CONTENT_TYPE_JSON = "application/json;charset=UTF-8"
        private const val EMPTY_JSON = "{}"
    }
}
