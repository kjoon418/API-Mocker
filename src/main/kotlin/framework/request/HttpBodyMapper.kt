package framework.request

interface HttpBodyMapper {
    fun map(rawBody: String, type: Class<*>): Any
}
