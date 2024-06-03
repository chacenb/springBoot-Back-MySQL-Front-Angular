package com.chace.serverManagement.Model.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class FtaExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException notValidException, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    log.info("handleMethodArgumentNotValid :: notValidException = {},\n headers = {},\n status = {},\n request = {}", notValidException, headers, status, request);

    /* get all the errors messages for clean returning */
    List<String> errorList = notValidException.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();

    return ResponseEntity.badRequest().body(
        ResponseStructure.builder()
            .timeStamp(LocalDateTime.now())
            .status(HttpStatus.valueOf(status.value()))
            .statusCode(status.value())
            .message(errorList.toString())
            .build()
    );
  }


  /* We can override many other methods coming from "ResponseEntityExceptionHandler" to define a custom bihavior if needed */

}

