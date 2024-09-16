package io.mountblue.blog_application_project.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGlobalException(Exception ex, Model model) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", "Something went wrong. Please check and  try again later.");
        return mav;
    }
}