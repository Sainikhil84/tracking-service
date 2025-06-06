# tracking-service
This service is related to tracking the orders

1) Clone the repository using the git clone command.
2) Import the cloned service into the workspace
3) Run the mvn install command to build the application
4) After the build is success run the application as Spring Boot Application
5) As the application will be running on port 8080 , context path as /orders and versioning v1 and the endpoint url /next-tracking-number and the query params trigger the below endpoint URL in postman.
6) http://localhost:8080/orders/v1/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2018-11-20T19:29:32%2B08:00&customer_id=de619854-b59b-324tefv&customer_name=Redbox%20Logistics&customer_slug=redbox-logistics
7) Since we need to validate the query parameters as per the request, spring boot starter validation is used to add the necessary validations, and these parameters should be properly validated.
8) If valid inputs are provided as per the requirements, the response will include a tracking number, date, and status, along with a 200 status code.
9) If any missing paramater there will be a 400 BAD request in response along with the missing paramter.
10) If an error occurs while generating the tracking number, a custom exception will be thrown and handled by the ExceptionHandler.
11) If any other error occurs handleAllException method in ExceptionHandler will handle it.


