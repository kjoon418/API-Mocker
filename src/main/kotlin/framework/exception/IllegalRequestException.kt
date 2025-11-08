package framework.exception

class IllegalRequestException(
    message: String,
    code: Int = 400
) : ResponsibleException(
    code = code,
    message = message
)
