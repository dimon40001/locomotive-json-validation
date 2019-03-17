/*
 * Locomotive Numbers Validator
 * Copyright (c) 2019.
 */

package com.dfedorov.locomotive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class which starts SpringBoot Tomcat and all dependencies.
 */
@SpringBootApplication
public class LocomotiveApplication {

    /**
     * Program main entry point.
     *
     * @param args command line arguments (parameters)
     */
    public static void main(String[] args) {
        SpringApplication.run(LocomotiveApplication.class, args);
    }

}
