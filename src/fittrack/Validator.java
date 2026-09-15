package fittrack;

import java.time.LocalDate;

public class Validator {

    public static boolean isPresent(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidFreeText(String value) {
        return !value.contains("#") && !value.contains(";");
    }

    public static boolean isValidName(String name) {
        return isPresent(name) && isValidFreeText(name);
    }

    public static boolean isValidHeight(double height) {
        return height >= 1.20 && height <= 2.30;
    }

    public static boolean isValidWeight(double weight) {
        return weight > 0 && weight <= 300;
    }

    public static boolean isValidMembershipDates(LocalDate startDate, LocalDate endDate) {
        return endDate.isAfter(startDate);
    }
}
