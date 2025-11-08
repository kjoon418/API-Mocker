package framework.request

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import framework.exception.IllegalRequestException

class JsonRequestBodyMapper(
    private val objectMapper: ObjectMapper = jacksonObjectMapper()
) : HttpBodyMapper {
    override fun map(rawBody: String, type: Class<*>): Any {
        if (rawBody.isBlank()) {
            return createEmptyInstance(type)
        }

        return objectMapper.readValue(rawBody, type)
    }

    private fun createEmptyInstance(type: Class<*>): Any {
        return try {
            type.getDeclaredConstructor().apply { isAccessible = true }.newInstance()
        } catch (e: Exception) {
            throw IllegalRequestException("body 매핑에 실패했습니다: ${type.name}")
        }
    }
}
