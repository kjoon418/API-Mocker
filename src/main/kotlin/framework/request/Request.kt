package framework.request

data class Request(
    val requestContext: RequestContext,
    val requestBody: RequestBody
) {
    val bearerToken: String? = requestContext.bearerToken
}
