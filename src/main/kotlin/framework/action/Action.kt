package framework.action

import framework.dto.Request

const val METHOD_NAME = "act"
const val PARAMETER_COUNT = 3

interface Action<in P, out R> {
    fun act(
        request: Request,
        pathVariables: Map<String, String> = mapOf(),
        body: P
    ): ActionResponse<R>
}
