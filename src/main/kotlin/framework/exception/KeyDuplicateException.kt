package framework.exception

import framework.constants.HttpStatus

class KeyDuplicateException(
    status: HttpStatus = HttpStatus.CONFLICT,
    message: String = "Key 중복이 발생했습니다."
) : ResponsibleException(
    status = status,
    message = message
)
