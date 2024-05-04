package ru.sfu.zooshop.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Getter;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import ru.sfu.zooshop.constraint.MatchingValues;

import java.util.Objects;

@Getter
public class MatchingValuesValidator implements ConstraintValidator<MatchingValues, Object> {
  private String fieldName;
  private String matchingFieldName;
  private String message;

  public void initialize(MatchingValues constraint) {
    this.fieldName = constraint.fieldName();
    this.matchingFieldName = constraint.matchingFieldName();
    this.message = constraint.message();
  }

  public boolean isValid(Object value, ConstraintValidatorContext context) {
    BeanWrapper beanWrapper = new BeanWrapperImpl(value);
    Object fieldValue;
    Object matchingFieldValue;

    try {
      fieldValue = beanWrapper.getPropertyValue(this.getFieldName());
      matchingFieldValue = beanWrapper.getPropertyValue(this.getMatchingFieldName());
    } catch (Exception exception) {
      return false;
    }

    boolean valid = Objects.equals(fieldValue, matchingFieldValue) && fieldValue != null;
    if (!valid) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(this.message)
        .addPropertyNode(this.matchingFieldName)
        .addConstraintViolation();
    }
    return valid;
  }
}
