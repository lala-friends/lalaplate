package org.lalafriends.lalaplate.common.config.exception

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice(annotations = [RestController::class])
class ControllerAdvice {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleValidationException(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest,
    ): Map<String, String> {
        log.error("BIND ERROR | ${ex.javaClass.simpleName} | ${ex.message} | ${request.servletPath}")
        return mapOf(
            "message" to ex.bindingResult.fieldErrors.firstNotNullOf { it.defaultMessage },
            "path" to (request.requestURI),
            "type" to (ex.javaClass.simpleName),
        )
    }

    @ExceptionHandler(value = [RuntimeException::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleRuntimeException(
        ex: RuntimeException,
        request: HttpServletRequest,
    ): Map<String, String> {
        log.error("RUNTIME ERROR | ${ex.javaClass.simpleName} | ${ex.message} | ${request.servletPath}")
        return mapOf(
            "message" to (ex.message ?: "RuntimeException!!"),
            "path" to (request.requestURI),
            "type" to (ex.javaClass.simpleName),
        )
    }
}
