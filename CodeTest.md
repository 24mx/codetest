# Code Test - Blog web & Blog client

This document tells about the two parts of the blog post application :

- Blog web : Java based back-end service that exposes rest end-points by using JAX-RS, Hibernate, and in-memory H2 database.
- Blog client : Vue Js based client app that consumes end-points by using axios.

**Dependencies**

- Blog web : This back-end service is dependent on the following

    - Java JDK version 11
    - Maven 3.6
    - Payara micro 5

- Blog client : This vue js app is dependent on the following

    - nodejs 14
    - npm 6
    - vue 3

**Steps**

Following steps can be followed to set up and run the api server and client.

- Blog web :

    1. Locate _pom.xml_ of blog-web module and run the maven command _mvn clean install_. This command will execute the unit test cases and generated a war file named _blog-web.war_
    2. Navigate to the location of _payara micro_ web server and execute the command to deploy the war file _java -jar {payara micro server jar file} --deploy {war file}_. This command will expose following end points connected to an in-memory h2 database on the url - _http://localhost:8080_.
       1. DELETE  /blog-web/posts
       2. GET     /blog-web/posts 
       3. POST    /blog-web/posts 
       4. PUT     /blog-web/posts 
       5. GET     /blog-web/posts/{postId}
    3. Locate _pom.xml_ of integration-test module and run the maven command _mvn clean test_. This command will execute RestAssured based integration test and verify the exposed end points on previous step.


- Blog client :

    1. Locate _package.json_ file of blog-client module and run the npm command _npm install_. This command will install node dependencies.
    2. Execute the command _npm run serve_ from the location of _package.json_ to start the build server of client app on the url - _http://localhost:8081_.
    3. Access the url exposed on the browser to use the client app.