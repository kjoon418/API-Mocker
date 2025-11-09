package framework.action

import framework.constants.HttpStatus

data class ActionResponse<out R>(
    val status: HttpStatus = HttpStatus.OK,
    val body: R?
)
