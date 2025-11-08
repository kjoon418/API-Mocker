package framework.dto

import framework.constants.HttpMethod

data class RequestContext(
    val method: HttpMethod,
    val path: String,
    val queryParams: Map<String, List<String>>,
    val headers: Map<String, List<String>>
)
