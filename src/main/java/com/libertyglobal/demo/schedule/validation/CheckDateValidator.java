package com.libertyglobal.demo.schedule.validation;

import com.libertyglobal.demo.schedule.model.Program;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CheckDateValidator implements ConstraintValidator<CorrectDateFormat, String> {
    private DateTimeFormatter formatter;

    public void initialize(CorrectDateFormat constraint) {
        formatter = DateTimeFormatter.ofPattern(constraint.pattern());
    }

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext constraintValidatorContext) {
        if (dateStr == null) {
            return true;
        }

        try {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
