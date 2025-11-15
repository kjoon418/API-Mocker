package framework.response

import framework.config.ConfigProperty
import jakarta.servlet.http.HttpServletResponse

class HttpResponseWriter {
    private val characterEncoding = ConfigProperty.CharacterEncoding.value

    fun write(httpResponse: HttpServletResponse, responseData: Response) {
        httpResponse.status = responseData.status.code
        httpResponse.contentType = responseData.contentType
        httpResponse.characterEncoding = characterEncoding

        httpResponse.writer.use { writer ->
            writer.write(responseData.body)
            writer.flush()
        }
    }
}
