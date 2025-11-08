package framework.request

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class JsonRequestBodyMapper(
    private val objectMapper: ObjectMapper = jacksonObjectMapper()
) : HttpBodyMapper {
    override fun map(rawBody: String, type: Class<*>): Any {
        if (type.isEmpty()) {
            return Unit
        }

        return objectMapper.readValue(rawBody, type)
    }

    private fun Class<*>.isEmpty(): Boolean {
        return this == Void::class.java || this == Void.TYPE || this == Unit::class.java
    }
}
