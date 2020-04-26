# brasilprev-api
This project content the test for BrasilPrev.

This API content some action to use in a e-commerce like as H2 configuration to save and manipulate data. REST services to create the endpoints for each actions. Data models like users, clients, products and orders.

With these services we can autenticate the user, create, update and delete clients and products. We can create, update, delete and update orders and we can add, remove, finish, cancel, alter the amount of a product, get the products exists in the order, get the order opened by client and get all orders exists in database.

The API content unit tests created by JUnit too, with these tests makes more easy to test all funcionalities.

# Access Database

To access the H2 Database you need to execute Spring Boot and use the following URL:
http://localhost:8080/v1/h2-console

# First User to Autentication

If you want to execute the endpoints using some IDE like Postman, Swagger or others, you need to insert the first user using the following insert on H2 and set the autentication that you create in the fields user and password to autenticate:

<h4>insert into USER (CPF, NAME, EMAIL, PASSWORD) values ('11122233344', 'Teste', 'teste@teste.com', 'teste');</h4>

In the first access the database you must input the following JDBC URL:
<h4>jdbc:h2:mem:testdb</h4>

But, if you just execute just the JUnits tests, It's not necessary execute this user insert.

# If use Eclipse to make test

If you decide to use the Eclipse IDE to make the tests, first is necessary downloading the Lombok, becouse I decide to use it to make easier use getters and setters.
You can download it in the website https://projectlombok.org/download

After download it you need to import the project as Maven Project and the API will be ready to test.

# If use just the command line

if you decide to use only the command line, just use the following command line:
mvn spring-boot:run

But for this, you need to have install de maven.

For more information, I included in the repository a power point that has all implementation like a flow. The archive name is Atividades Teste Brasilprev.pptx. In this archive I describe all endpoints and your respective JSON.
