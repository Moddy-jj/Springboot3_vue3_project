package com.example.anno;
import com.example.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
@Documented
@Constraint(
   validatedBy = {StateValidation.class}//指定校验器
)
@Target({ FIELD})//元注解
@Retention(RUNTIME)
public @interface State {
    //校验失败时返回的消息
    String message() default "state 参数只能为已发布或草稿";

    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};

}
