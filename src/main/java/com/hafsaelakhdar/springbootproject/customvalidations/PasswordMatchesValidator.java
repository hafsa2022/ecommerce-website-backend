package com.hafsaelakhdar.springbootproject.customvalidations;


import com.hafsaelakhdar.springbootproject.exceptions.PasswordMismatchException;
import com.hafsaelakhdar.springbootproject.request.SignUpRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        SignUpRequest user = (SignUpRequest) obj;

        if (!user.getPassword().equals(user.getMatchingPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        return true;
    }
}