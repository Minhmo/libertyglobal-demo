package com.libertyglobal.demo.schedule.validation;

import com.libertyglobal.demo.schedule.model.Program;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CorrectRangeValidator implements ConstraintValidator<CorrectRange, Program> {
    @Override
    public void initialize(CorrectRange constraintAnnotation) {

    }

    @Override
    public boolean isValid(Program program, ConstraintValidatorContext constraintValidatorContext) {
        if (program.getStart() == null || program.getEnd() == null) {
            return false;
        }

        // usually programs does not last 24h, so we validate.
        if (program.getEnd().equals(program.getStart())) {
            return false;
        }

        return true;
    }
}
