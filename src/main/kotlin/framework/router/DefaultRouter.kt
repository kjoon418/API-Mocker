package framework.router

import framework.action.Action
import framework.action.Actions
import framework.exception.IllegalRequestException
import framework.request.Request

class DefaultRouter(
    private val actions: Actions
) : Router {
    override fun route(request: Request): Pair<RouteKey, Action<*, *>> {
        val method = request.requestContext.method
        val path = request.requestContext.path

        return actions.find(method, path)
            ?: throw IllegalRequestException("요청 [$method $path] 와 일치하는 액션을 찾을 수 없습니다.")
    }
}
