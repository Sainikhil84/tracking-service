# tracking-service

1)Clone the repository using the git clone command.
2)Import the cloned service into your workspace.
3)Run the mvn install command to build the application.
4)After the build is successful, run the application as a Spring Boot application.
5)Since the application will be running on port 8080 with the context path /orders, version v1, and the endpoint URL /next-tracking-number accepting query parameters, trigger the following endpoint URL in Postman.
6) http://localhost:8080/orders/v1/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2018-11-20T19:29:32%2B08:00&customer_id=de619854-b59b-324tefv&customer_name=Redbox%20Logistics&customer_slug=redbox-logistics
7) Since we need to validate the query parameters as per the request, spring boot starter validation is used to add the necessary validations, and these parameters should be properly validated.
8) If valid inputs are provided as per the requirements, the response will include a tracking number, date, and status, along with a 200 status code.
9) If any missing parameter there will be a 400 BAD request in response along with the missing parameter.
10) If an exception occurs while generating the tracking number, a custom exception will be thrown and handled by the ExceptionHandler.
11) If any other exception occurs, the handleAllException method in the ExceptionHandler will handle it.
12) To deploy the application as a container DockerFile has been used.
13) Added Junit test cases for code coverage

AWS Cloud Deployment Update:
===========================
1) The image has been pushed to AWS ECR
2) Created a cluster and a task definition in ECS.
3) Created a Service with 2 instances with auto-scaling and load balancing
4) Deployment is failing due to some issues checking the same.
5) Sample AWS URL with public IP
http://107.23.60.224/orders/v1/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2018-11-20T19:29:32%2B08:00&customer_id=de619854-b59b-324tefv&customer_name=Redbox%20Logistics&customer_slug=redbox-logistics


