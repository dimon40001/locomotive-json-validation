/*
 * Locomotive Numbers Validator
 * Copyright (c) 2019.
 */

package com.dfedorov.locomotive.model;

import com.dfedorov.locomotive.model.LocomotiveNumber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LocomotiveNumberTests {

    private LocomotiveNumber[] arr;
    private boolean[] result;

    @Before
    public void setUp() {
        arr = new LocomotiveNumber[]{
                new LocomotiveNumber(1014, 5, 1),
                new LocomotiveNumber(1116, 64, 5),
                new LocomotiveNumber(1142, 606, 8),
                new LocomotiveNumber(1142, 606, 1)
        };
        result = new boolean[]{true, true, false, true};
    }

    @Test
    public void testToString() {
        for (int i = 0; i < arr.length; i++) {
            Assert.assertEquals(arr[i].toString(), arr[i].getFullNumber());
        }
    }

    @Test
    public void testIsValid() {
        for (int i = 0; i < arr.length; i++) {
            Assert.assertEquals(arr[i].isValid(), result[i]);
        }
    }

    @Test
    public void testFullNumber() {
        Assert.assertEquals(arr[1].getFullNumber(), "1116 064-5");
    }


}
