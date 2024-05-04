package ru.sfu.zooshop.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.sfu.zooshop.constraint.MatchingValues.List;
import ru.sfu.zooshop.validator.MatchingValuesValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The values of the two fields (of the annotated type) with given names
 * ({@link #fieldName()} and {@link #matchingFieldName()}) must match exactly.
 * They will be compared with {@code Objects.equals()}.
 * The fields with given names must be present and of the same type
 *
 * @author Andrey Nosov
 * @since 1.0
 * @see MatchingValuesValidator
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
@Repeatable(List.class)
@Constraint(validatedBy = MatchingValuesValidator.class)
public @interface MatchingValues {
  /**
   * @return the name of the field whose value must match the {@link #matchingFieldName()} value
   */
  String fieldName();

  /**
   * @return the name of the field whose value must match the {@link #fieldName()} value
   */
  String matchingFieldName();

  String message() default "Fields' values do not match";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /**
   * Defines several {@code @MatchingValues} constraints on the same type
   *
   * @see MatchingValues
   */
  @Documented
  @Target(TYPE)
  @Retention(RUNTIME)
  @interface List {
    MatchingValues[] value();
  }
}
