package framework.router

import framework.action.Action
import framework.dto.Request

interface Router {
    fun route(request: Request): Pair<RouteKey, Action<*, *>>
}
