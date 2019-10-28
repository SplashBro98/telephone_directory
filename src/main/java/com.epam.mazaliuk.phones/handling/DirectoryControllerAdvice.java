package com.epam.mazaliuk.phones.handling;

import com.epam.mazaliuk.phones.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DirectoryControllerAdvice {

    private static Logger logger = LoggerFactory.getLogger(DirectoryControllerAdvice.class);

    @ExceptionHandler(BaseException.class)
    public ModelAndView handle(BaseException e) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return createModelAndView(e, status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handle(IllegalArgumentException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return createModelAndView(e, status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ModelAndView handle(HttpMessageNotReadableException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return createModelAndView(e, status);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handle(NumberFormatException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return createModelAndView(e, status);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handle(NoHandlerFoundException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return createModelAndView(e, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handle(MethodArgumentNotValidException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return createModelAndView(e, status);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handle(RuntimeException e) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return createModelAndView(e, status);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView handle(HttpRequestMethodNotSupportedException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return createModelAndView(e, status);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ModelAndView handle(HttpMediaTypeNotSupportedException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return createModelAndView(e, status);
    }

    private ModelAndView createModelAndView(Exception ex, HttpStatus status) {

        logger.error(ex.getMessage());
        ErrorMessage message = new ErrorMessage(status.toString(), ex.getLocalizedMessage());
        Map<String, String> map = new HashMap<>();
        map.put("message", message.getMessage());

        return new ModelAndView("error", map);
    }
}
