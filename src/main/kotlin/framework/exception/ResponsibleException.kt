package framework.exception

import framework.constants.HttpStatus

abstract class ResponsibleException(
    val status: HttpStatus,
    message: String
) : Exception(message)
