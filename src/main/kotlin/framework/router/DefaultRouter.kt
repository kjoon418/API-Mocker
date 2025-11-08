package framework.router

import framework.action.Action
import framework.constants.HttpMethod
import framework.dto.Request
import framework.exception.IllegalRequestException

class DefaultRouter(
    private val routes: Map<RouteKey, Action<*, *>>
) : Router {
    override fun route(request: Request): Pair<RouteKey, Action<*, *>> {
        val method = request.requestContext.method
        val path = request.requestContext.path

        return routes.filterByMethod(method)
            .findMatchAction(path)
            ?: throw IllegalRequestException("요청 [$method $path] 와 일치하는 액션을 찾을 수 없습니다.")
    }

    private fun Map<RouteKey, Action<*, *>>.filterByMethod(
        method: HttpMethod
    ): List<Map.Entry<RouteKey, Action<*, *>>> {
        return entries.filter { (key, _) ->
            key.method == method
        }
    }

    private fun List<Map.Entry<RouteKey, Action<*, *>>>.findMatchAction(
        path: String
    ): Pair<RouteKey, Action<*, *>>? {
        for ((key, action) in this) {
            if (key.pathTemplate.matches(path)) {
                return key to action
            }
        }

        return null
    }
}
