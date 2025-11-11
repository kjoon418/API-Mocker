package framework.dispatcher

import framework.action.Action
import framework.action.ActionResponse
import framework.action.METHOD_NAME
import framework.action.PARAMETER_COUNT
import framework.dto.Request
import framework.dto.Response
import framework.exception.ExceptionResponseHandler
import framework.request.HttpBodyMapper
import framework.response.ResponseMapper
import framework.router.PathVariableExtractor
import framework.router.Router
import framework.security.RequireAuthorize
import framework.security.validateToken
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

class Dispatcher(
    private val responseMapper: ResponseMapper,
    private val bodyMapper: HttpBodyMapper,
    private val router: Router,
    private val pathVariableExtractor: PathVariableExtractor,
    private val exceptionResponseHandler: ExceptionResponseHandler
) {
    fun dispatch(request: Request): Response {
        try {
            val (routeKey, action) = router.route(request)
            checkAuthorization(action, request.bearerToken)

            val type = findTypeOf(action)
            val body = bodyMapper.map(request.requestBody.body, type)
            val pathVariables = pathVariableExtractor.extract(routeKey, request.requestContext.path)

            val actionResponse = runAction(
                action = action,
                request = request,
                pathVariables = pathVariables,
                body = body
            )

            return responseMapper.map(actionResponse)
        } catch (exception: Exception) {
            return exceptionResponseHandler.createErrorResponse(exception)
        }
    }

    private fun checkAuthorization(action: Action<*, *>, token: String?) {
        val actMethod = action.actMethod
            ?: throw IllegalStateException("Action에서 act 메서드를 찾을 수 없습니다: ${this::class.java.name}")

        val annotation = actMethod.getAnnotation(RequireAuthorize::class.java)

        if (annotation != null) {
            validateToken(token, annotation.role)
        }
    }

    private val Action<*, *>.actMethod: Method?
        get() {
            return this::class.java.methods.find {
                it.name == METHOD_NAME &&
                        it.parameterCount == PARAMETER_COUNT &&
                        !it.isBridge
            }
        }

    private fun findTypeOf(action: Action<*, *>): Class<*> {
        val superclass = action.javaClass.genericInterfaces
            .firstOrNull { it is ParameterizedType } as? ParameterizedType

        return superclass?.actualTypeArguments?.first() as Class<*>
    }

    @Suppress("UNCHECKED_CAST")
    private fun runAction(
        action: Action<*, *>,
        request: Request,
        pathVariables: Map<String, String>,
        body: Any
    ): ActionResponse<*> {
        return (action as Action<Any?, *>).act(request, pathVariables, body)
    }
}
