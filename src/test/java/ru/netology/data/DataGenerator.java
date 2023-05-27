package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    public static Faker fakerEN = new Faker(new Locale("en"));
    public static Faker fakerRU = new Faker(new Locale("ru"));

    public static String getApprovedNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getInvalidCardNumber() {
        return "4444 4444 4444 4443";
    }

    public static String getShortCardNumber() {
        return "4444 4444 4444 444";
    }


    public static String getCurrentMonth() {
        return String.format("%02d", LocalDate.now().getMonthValue());
    }

    public static String get_OO_Month() {
        return "00";
    }

    public static String get_13_Month() {
        return "13";
    }

    public static String getLastMonth() {
        return LocalDate.now().minusMonths(1)
                .format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getCurrentYear() {
        return String.format("%ty", Year.now());
    }

    public static String getLastYear() {
        return String.format("%ty", LocalDate.now().minusYears(1));
    }

    public static String getNextYear() {
        return String.format("%ty", LocalDate.now().plusYears(1));
    }

    public static String getValidName() {
        return fakerRU.name().firstName() + " " + fakerRU.name().lastName();
    }

    public static String getNameAndSurnameDash() {
        return fakerRU.name().firstName() + "-" + fakerRU.name().lastName();
    }

    public static String getDoubleSurname() {
        return fakerRU.name().firstName() + " " + fakerRU.name().lastName() + "-" + fakerRU.name().lastName();
    }

    public static String getValidNameInLatinLetters() {
        return fakerEN.name().firstName() + " " + fakerEN.name().lastName();
    }

    public static String getOnlyName() {
        return fakerRU.name().firstName();
    }

    public static String getOnlyNameInLatinLetters() {
        return fakerEN.name().firstName();
    }

    public static String getOnlySurname() {
        return fakerRU.name().lastName();
    }

    public static String getOnlySurnameInLatinLetters() {
        return fakerEN.name().lastName();
    }

    public static String getNameWithNumbers() {
        return fakerEN.number().digits(13);
    }

    public static String getNameShort() {
        return fakerEN.lorem().characters(1).toUpperCase();
    }

    public static String getNameLong() {
        return fakerEN.lorem().characters(200);
    }

    public static String getNameSymbol() {
        return "@*&?()_+/~\"\".,№";
    }

    public static String getValidCvc() {
        return fakerEN.numerify("###");
    }

    public static String getCvcWithOneDigit() {
        return fakerEN.numerify("#");
    }

    public static String getCvcWithTwoDigits() {
        return fakerEN.numerify("##");
    }

}