package Filmdatabase.validation;

public class MovieValidator {

    public void validateYear(int year) {
        if (year < 1888 || year > 2100) {
            throw new IllegalArgumentException("Ungültiges Jahr");
        }
    }

    public void validateRuntime(int runtime) {
        if (runtime <= 0) {
            throw new IllegalArgumentException("Laufzeit muss > 0 sein");
        }
    }
}
