package framework.exception

import framework.constants.HttpStatus

class UnauthorizedException(
    status: HttpStatus = HttpStatus.UNAUTHORIZED,
    message: String = "권한이 없습니다."
) : ResponsibleException(status, message)
