package framework.router

import framework.action.Action
import framework.request.Request

interface Router {
    fun route(request: Request): Pair<RouteKey, Action<*, *>>
}
