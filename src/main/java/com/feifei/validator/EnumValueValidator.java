package com.qding.bigdata.validator;

import com.qding.bigdata.annotations.validation.EnumValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author yanpf
 * @date 2019/6/12 14:57
 * @description
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {

    private String[] strValues;
    private int[] intValues;

    @Override
    public void initialize(EnumValue enumValue) {
        this.strValues = enumValue.strValues();
        this.intValues = enumValue.intValues();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof String) {
            for (String s : strValues) {
                if (s.equals(value)) {
                    return true;
                }
            }
        } else if (value instanceof Integer) {
            for (Integer s : intValues) {
                if (s == value) {
                    return true;
                }
            }
        }
        return false;
    }
}
