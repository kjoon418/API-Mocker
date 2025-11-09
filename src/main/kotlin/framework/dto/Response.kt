package framework.dto

import framework.constants.HttpStatus

data class Response(
    val status: HttpStatus,
    val contentType: String,
    val body: String
)
