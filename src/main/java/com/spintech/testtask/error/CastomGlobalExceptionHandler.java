package com.spintech.testtask.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CastomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PersonNotFoundException.class, TvShowNotFoundException.class})
    public void SpringHandlerNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

}
