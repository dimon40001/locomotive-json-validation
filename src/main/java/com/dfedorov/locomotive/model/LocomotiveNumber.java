/*
 * Locomotive Numbers Validator
 * Copyright (c) 2019.
 */

package com.dfedorov.locomotive.model;

/**
 * Definition of the railway train number.
 * <p>
 * <p>Train number example: 1014 005-1, where:
 * <p>1014 - Reihennummer
 * <p>005 - Ordnungsnummer
 * <p>1 - Check digit
 * <p>
 * <p>https://de.wikipedia.org/wiki/Reihenschema_der_%C3%96BB
 */
public class LocomotiveNumber implements Checkable {

    /**
     * 4 digits (Reihennummer)
     */
    private int reihenNumber;

    /**
     * 3 digits (Ordnungsnummer)
     */
    private int ordnungsNumber;

    /**
     * Check digit (Prüfziffer)
     */
    private int checkDigit;

    /**
     * User-friendly string representation of the locomotive number
     */
    private String fullNumber;

    /**
     * Validity status
     */
    private boolean isValid;

    /**
     * LocomotiveNumber constructor.
     * <p>
     * Sets the initial parameters and updates instance variables
     *
     * @param reihenNumber   Locomotive's Reihennummer
     * @param ordnungsNumber Locomotive's Ordnungsnummmer
     * @param checkDigit     Locomotive's Prüfziffer
     */
    public LocomotiveNumber(int reihenNumber, int ordnungsNumber, int checkDigit) {
        this.reihenNumber = reihenNumber;
        this.ordnungsNumber = ordnungsNumber;
        this.checkDigit = checkDigit;
        this.fullNumber = getFullNumber();
        this.isValid = isValid();
    }

    /**
     * @return User-friendly locomotive number with leading zeros
     */
    @Override
    public String toString() {
        return fullNumber;
    }

    /**
     * @return User-friendly locomotive number with leading zeros
     */
    public String getFullNumber() {
        return String.format("%04d %03d-%1d", reihenNumber, ordnungsNumber, checkDigit);
    }

    /**
     * Implementation of the Checkable interface method
     *
     * @return "true" if the LocomotiveNumber is valid
     */
    @Override
    public boolean isValid() {
        return checkDigit == getCheckNumber();
    }

    /**
     * Implementation of the Checkable interface method
     * <p>
     * Bei den ÖBB ist das eine achtstellige Nummer bestehend aus der vierstelligen Reihennummer,
     * der dreistelligen Ordnungsnummer und einer durch einen Bindestrich abgesetzten Prüfziffer.
     * Die Prüfziffer wird aus den ersten sieben Stellen berechnet.
     * Dazu wird die Quersumme der Ziffernfolge gebildet, die sich ergibt, wenn man die sieben Ziffern
     * abwechselnd mit 2 und 1 multipliziert (erste Stelle mit 2, zweite mit 1, dritte wieder mit 2 usw.);
     * die Differenz dieser Quersumme <b>zum nächsten Vielfachen von Zehn</b> bildet die Prüfziffer.
     * Bei der Eingabe in Rechner wird über die Prüfziffer eine Plausibilitätskontrolle ausgeführt,
     * die beispielsweise Ziffernstürze erkennt.
     *
     * @return check digit
     */
    @Override
    public int getCheckNumber() {
        final int multipleOf10 = 10;
        int checkSum = getCheckSum(reihenNumber, 1) + getCheckSum(ordnungsNumber, 2);
        return multipleOf10 - checkSum % 10;
    }

    /**
     * Reverts multiplier value from 1 to 2, from 2 to 1 and so forth.
     *
     * @param multiplier
     * @return multiplier
     */
    private int getMultiplier(int multiplier) {
        if (multiplier == 2) {
            multiplier = 1;
        } else {
            multiplier = 2;
        }
        return multiplier;
    }

    /**
     * Calculates the checksum for the provided value and returns as integer.
     *
     * @param number     value to calculate checksum
     * @param multiplier initial multiplier to start with
     *                   (for 4-digit numbers should be 1,
     *                   for 3-digit numbers should be 2,
     * @return Checksum of the number
     */
    private int getCheckSum(int number, int multiplier) {
        int sum = 0;

        while (number != 0) {
            int digit = number % 10;
            digit *= multiplier;

            if (digit > 9) {
                sum += 1 + digit % 10;
            } else {
                sum += digit;
            }

            multiplier = getMultiplier(multiplier);
            number /= 10;
        }
        return sum;
    }
}
