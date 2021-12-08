package com.anonymous.appilogue.exceptions

sealed class NetworkException(open val code: Int, message: String) : Exception(message)

/*
Http 응답 코드가 400 번대 Error
 */
data class ClientErrorException(override val code: Int, override val message: String) : NetworkException(code, message)

/*
Http 응답 코드가 500 번대 Error
 */
internal data class ServerErrorException(override val code: Int, override val message: String) : NetworkException(code, message)

/*
응답값이 null 로 내려오는 경우를 처리하기 위한 Error
 */
internal data class EmptyResponseException(override val code: Int, override val message: String) : NetworkException(code, message)

/*
그 외의 Error
 */
internal data class UnknownException(override val code: Int, override val message: String) : NetworkException(code, message)