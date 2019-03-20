/*
 * Locomotive Numbers Validator
 * Copyright (c) 2019.
 */

package com.dfedorov.locomotive;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dfedorov.locomotive.model.LocomotiveNumber;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller class takes care of HTTP GET requests and sends responses.
 */
@RestController
public class LocomotiveController {

    /**
     * Default web-page mapping for server root ("/")
     */
    private final String INDEX_PAGE =
            "<!DOCTYPE html>" +
            "<html><head></head><body>" +
            "Use the following format: <br><br>" +
            "/api?locomotive=12345678" +
            "<br></body></html>";
    private final String BAD_REQUEST = "Bad Request";

    /**
     * Sends default response when server root is accessed.
     *
     * @return web-page body
     */
    @GetMapping(value = "/", produces = "text/html; charset=UTF-8")
    public String index() {
        return INDEX_PAGE;
    }

    /**
     * HTTP mapping for /api which sends the JSON output for the requested locomotive full number.
     *
     * @param locomotive locomotive full number (8 digits). Example: 11426061
     * @param request    HTTP request
     * @param response   HTTP response
     * @return response body
     */
    @GetMapping(value = "/api", produces = "application/json;charset=UTF-8")
    public String api(@RequestParam(value = "locomotive", required = false) String locomotive,
                      HttpServletRequest request,
                      HttpServletResponse response) {
        String result = "";
        if (locomotive == null || locomotive.length() != 8) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            result = BAD_REQUEST;
        } else {
            try {
                int reihenNumber = Integer.parseInt(locomotive.substring(0, 4));
                int ordnungsNumber = Integer.parseInt(locomotive.substring(4, 7));
                int checkDigit = Integer.parseInt(locomotive.substring(7, 8));
                response.setStatus(HttpServletResponse.SC_OK);
                result = new Gson().toJson(new LocomotiveNumber(
                        reihenNumber,
                        ordnungsNumber,
                        checkDigit));
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                result = BAD_REQUEST;
            }
        }
        return result;
    }

}
