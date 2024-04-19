package com.example.validation;
import com.example.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class StateValidation implements ConstraintValidator<State, String> {
    /**
     * 
     * @param value 校验数据
     * @param context   
     * 
     * @return false:校验失败 true:校验成功
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //校验逻辑
        if (value==null){
            return false;
        
        }
        if (value.equals("已发布")||value.equals("草稿")){
            return true;
        }
        return false;
    }
}
