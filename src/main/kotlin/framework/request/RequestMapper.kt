package framework.request

interface RequestMapper<T> {
    fun map(request: T): Request
}
