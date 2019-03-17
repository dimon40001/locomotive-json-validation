/*
 * Locomotive Numbers Validator
 * Copyright (c) 2019.
 */

package com.dfedorov.locomotive.model;

/**
 * Interface defines common methods for the validity checks.
 */
public interface Checkable {

    /**
     * Checks the validity of the object's internal state.
     *
     * @return "true" if the object is in the valid state, "false" otherwise
     */
    boolean isValid();

    /**
     * Calculates the check number (control number, check sum) for the object.
     *
     * @return the control number (check sum) calculated by the object
     */
    int getCheckNumber();
}
