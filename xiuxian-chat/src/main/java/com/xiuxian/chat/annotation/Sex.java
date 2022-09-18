package com.xiuxian.chat.annotation;



import com.xiuxian.chat.validator.SexValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SexValid.class)
public @interface Sex {
	// 错误提示信息
    String message() default "性别填写错误,可填写值'男'或'女'";
    
    Class<?>[] groups() default {};
	
    Class<? extends Payload>[] payload() default {};
}
