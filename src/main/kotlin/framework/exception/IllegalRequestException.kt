package framework.exception

import framework.constants.HttpStatus

class IllegalRequestException(
    message: String,
    status: HttpStatus = HttpStatus.BAD_REQUEST
) : ResponsibleException(
    status = status,
    message = message
)
