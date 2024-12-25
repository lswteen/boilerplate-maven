package com.farfarcoder.web.user.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class StringNameValidator implements ConstraintValidator<StringName, String> {

    private static final Pattern ALLOW_STRING = Pattern.compile("""
                 ^[A-Za-z0-9]+$
                 """.trim());

    private static final Pattern EMAIL_PATTERN = Pattern.compile("""
        ^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$
        """.trim());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        var hasText = StringUtils.hasText(value);
        var hasLength = StringUtils.hasLength(value);

        if (hasLength && !hasText) {
            return false;
        } else if (!hasText) {
            return true;
        }

        return EMAIL_PATTERN.matcher(value).matches();
    }
}

