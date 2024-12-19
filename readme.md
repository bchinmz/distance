# PostcodeApplication

This **PostcodeApplication** will populate in memory H2 database 
based on ukpostcodes.csv file upon start up

Swagger doc: http://localhost:8080/swagger-ui/index.html#/

| Endpoints | Description |
| ----------- | ----------- |
| GET /distances/calc | Calculates the distance between 2 postcodes |
| GET /postcodes/{postcode} | Retrieve the coordinates based on postcode |
| PUT /postcodes/{postcode} | Updates the coordinates based on postcode |