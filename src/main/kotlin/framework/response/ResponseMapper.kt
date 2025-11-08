package framework.response

import framework.action.ActionResponse
import framework.dto.Response

interface ResponseMapper {
    fun map(actionResponse: ActionResponse<*>): Response
}
