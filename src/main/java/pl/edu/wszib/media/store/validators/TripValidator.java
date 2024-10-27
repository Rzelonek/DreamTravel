package pl.edu.wszib.media.store.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.edu.wszib.media.store.exceptions.TripValidationException;

public class TripValidator {

    public static void validateTitle(String title) {
        String regex = "^[A-Z].*$";
        if (!title.matches(regex)) {
            throw new TripValidationException();
        }
    }

    public static void validateDirection(String direction) {
        String regex = "^[A-Z][a-z]{2,} [A-Z][a-z]+$";
        if (!direction.matches(regex)) {
            throw new TripValidationException();
        }
    }

    public static void validateGenderType(String gender) {
        String regex = "^(Last Minute|All inclusive|Egzotyka|Popularne|Nowa|Objazdowa)$";
        if (!gender.matches(regex)) {
            throw new TripValidationException();
        }
    }
}
