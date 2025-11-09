package framework.response

import framework.config.CHARACTER_ENCODING
import framework.dto.Response
import jakarta.servlet.http.HttpServletResponse

class HttpResponseWriter {
    fun write(httpResponse: HttpServletResponse, responseData: Response) {
        httpResponse.status = responseData.status.code
        httpResponse.contentType = responseData.contentType
        httpResponse.characterEncoding = CHARACTER_ENCODING

        httpResponse.writer.use { writer ->
            writer.write(responseData.body)
            writer.flush()
        }
    }
}
