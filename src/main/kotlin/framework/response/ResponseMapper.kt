package framework.response

import framework.action.ActionResponse

interface ResponseMapper {
    fun map(actionResponse: ActionResponse<*>): Response
}
