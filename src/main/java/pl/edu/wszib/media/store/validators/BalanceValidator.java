package pl.edu.wszib.media.store.validators;

import pl.edu.wszib.media.store.exceptions.BalanceValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class BalanceValidator {

      // Validate security code (must be 3 digits)
      public static void validateSecurityCode(String securityCode) {
            String regex = "^\\d{3}$";
            if (!Pattern.matches(regex, securityCode)) {
                  throw new BalanceValidationException("Security code must consist of exactly 3 digits.");
            }
      }

      // Validate expiration date (must be in MM/YY format and later than the current
      // date)
      public static void validateExpirationDate(String expirationDate) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            try {
                  LocalDate date = LocalDate.parse("01/" + expirationDate, DateTimeFormatter.ofPattern("dd/MM/yy"));
                  if (date.isBefore(LocalDate.now())) {
                        throw new BalanceValidationException("Expiration date must be in the future.");
                  }
            } catch (DateTimeParseException e) {
                  throw new BalanceValidationException("Invalid expiration date format. Use MM/YY.");
            }
      }
}
