package com.sunray.common.util;

import com.sunray.common.expection.ValidationException;
import com.sunray.entity.modal.ParkSlot;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidationUtil {


    public static void validate(Object obj) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(obj);
        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<Object> violation : violations) {
            errorMessage.append(violation.getMessage()).append("; ");
        }
        if (errorMessage.length() > 0) {
            throw new ValidationException(errorMessage.toString());
        }
    }

    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        ParkSlot parkSlot = new ParkSlot(null, null, null);
        validate(parkSlot);
    }


}
