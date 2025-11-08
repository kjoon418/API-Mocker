package framework.exception

class IllegalRequestException(
    message: String
) : ResponsibleException(
    code = 400,
    message = message
)
