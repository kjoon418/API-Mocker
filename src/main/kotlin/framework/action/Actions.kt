package framework.action

import framework.constants.HttpMethod
import framework.exception.ApplicationConfigFailException
import framework.router.RouteKey

class Actions {
    private val actions = mutableMapOf<RouteKey, Action<*, *>>()

    fun register(
        routeKey: RouteKey,
        actionInstance: Action<*, *>
    ) {
        validateDuplicate(routeKey)

        actions[routeKey] = actionInstance
    }

    fun find(
        method: HttpMethod,
        path: String
    ): Pair<RouteKey, Action<*, *>>? {
        return actions
            .filter { it.key.method == method }
            .filter { it.key.pathTemplate.matches(path) }
            .map { Pair(it.key, it.value) }
            .firstOrNull()
    }

    private fun validateDuplicate(routeKey: RouteKey) {
        val existAction = actions[routeKey]

        if (existAction != null) {
            throw ApplicationConfigFailException("$DUPLICATE_ROUTE_KEY : $routeKey")
        }
    }

    companion object {
        private const val DUPLICATE_ROUTE_KEY = "Action의 라우팅 정보가 중복되었습니다."
    }
}
