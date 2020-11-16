package com.jadan.client.utils;

import com.jadan.client.model.view.ClientRequest;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {


    public static int calculateAge(LocalDate birthdate) {
        LocalDate now = LocalDate.now();
        Period diff = Period.between(birthdate, now);
        return  diff.getYears();
    }

    public static LocalDate toLocalDate(String birthdate) {
        try {
            return LocalDate.parse(birthdate, DateTimeFormatter.ofPattern(ClientRequest.PATTERN));
        } catch (Exception e) {
            return null;
        }
    }
    public static boolean isValidDate(String birthdate) {

        DateTimeFormatter d = DateTimeFormatter.ofPattern(ClientRequest.PATTERN);
        try {
            d.parse(birthdate);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


}
