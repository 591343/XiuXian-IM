package com.xiuxian.chat.validator;

import com.xiuxian.chat.annotation.Sex;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SexValid implements ConstraintValidator<Sex, Integer> {

    /**
     * 如果匹配返回true 不匹配返回false
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return value==0||value==1;
    }
}
