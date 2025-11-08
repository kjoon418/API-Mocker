package framework.exception

abstract class ResponsibleException(
    val code: Int,
    message: String
) : Exception(message)
