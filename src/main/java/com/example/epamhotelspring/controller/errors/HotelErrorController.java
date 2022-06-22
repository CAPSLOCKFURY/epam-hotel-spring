package com.example.epamhotelspring.controller.errors;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
@Controller
public class HotelErrorController implements ErrorController {

    @ExceptionHandler(Throwable.class)
    public String handleError(){
        return "error";
    }

    @RequestMapping("/error")
    public String handleViewError(){
        return "error";
    }


}
