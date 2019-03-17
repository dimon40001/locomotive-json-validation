# locomotive-json-validation
Web-service for the locomotive numbers validity online check (based on SpringBoot).

## How to use
1. Start the application
1. Navigate to `http://localhost:8080/api?locomotive=<locomotive_number>`
1. Get JSON

**Example**
`http://localhost:8080/api?locomotive=1160645`
JSON Result:
```json
{
	"reihenNumber": 1116,
	"ordnungsNumber": 64,
	"checkDigit": 5,
	"fullNumber": "1116 064-5",
	"isValid": true
}
```

***

# Description

- Please implement a service, using Spring Boot, that provides a REST API for validating locomotive numbers
(details below).
- The service should run on Java 8 or newer. Build system can be Maven or Gradle.
- Unit / API-Tests are a plus, but not a must.
- Provide your results via GitHub or similar

## The algorithm for validating locomotive numbers should work as follows:

The check digit is calculated from the first seven digits. For this purpose, the sum of digits of the number sequence is
formed, which is obtained by multiplying the seven digits alternately by 2 and 1 (first digit with 2, second with 1, third
again with 2, etc.); the difference of this checksum to the next multiple of ten forms the check digit. When entering
the calculator, the check digit is used to carry out a plausibility check, which detects, for example, numerical falls.

## Examples

### Locomotive number: 1014 005-1
    Number: 1 0 1 4 0 0 5
    Multiplier: 2 1 2 1 2 1 2
    Result: 2 0 2 4 0 0 10
    Checksum: 2+ 0+ 2+ 4+ 0+ 0+ 1+0 = 9
    Difference to the nearest multiple of 10: (10-9=) 1
    1 = Check digit -> *1014 005-1*

### Locomotive number: 1116 064-5
    Number: 1 1 1 6 0 6 4
    Multiplier: 2 1 2 1 2 1 2
    Result: 2 1 2 6 0 6 8
    Checksum: 2+ 1+ 2+ 6+ 0+ 6+ 8 = 25
    Difference to the nearest multiple of 10: (30-25=) 5
    5 = Check digit -> *1116 064-5*

## See also
Source: https://de.wikipedia.org/wiki/Reihenschema_der_%C3%96BB
