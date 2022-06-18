package com.codecool.trainscheduleapi;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidationUtility {
    public static String getFieldErrorMessages(BindingResult errors) {
        String errorMessage = "";
        for (FieldError fieldError : errors.getFieldErrors()) {
            errorMessage += (errorMessage.length() == 0 ? "" : "\n") + fieldError.getDefaultMessage();
        }
        return errorMessage;
    }
}
