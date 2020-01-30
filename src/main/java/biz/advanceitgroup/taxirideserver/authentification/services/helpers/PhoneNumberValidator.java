package biz.advanceitgroup.taxirideserver.authentification.services.helpers;

import biz.advanceitgroup.taxirideserver.authentification.annotations.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<Phone, String> {

    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PHONE ="(^$|[0-9]{10})";
    @Override
    public void initialize(Phone constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String mobile, ConstraintValidatorContext context) {
        pattern = Pattern.compile(EMAIL_PHONE);
        if( mobile==null){
            return false;
        }
        matcher = pattern.matcher(mobile);
        return matcher.matches();

    }
}
