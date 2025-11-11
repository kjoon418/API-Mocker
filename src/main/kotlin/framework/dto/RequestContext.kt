package framework.dto

import framework.constants.HttpMethod

data class RequestContext(
    val method: HttpMethod,
    val path: String,
    val queryParams: Map<String, List<String>>,
    val headers: Map<String, List<String>>
) {
    val bearerToken: String? = headers[AUTHORIZATION]?.firstOrNull()?.removePrefix(BEARER_PREFIX)

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }
}
