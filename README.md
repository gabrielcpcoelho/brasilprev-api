# brasilprev-api
This project content the test for BrasilPrev.
This API content some action to use in a e-commerce like as H2 configuration to save and manipulate data. REST services to create the endpoints for each actions. Data models like users, clients, products and orders.
With these services we can autenticate the user, create, update and delete clients and products. We can create, update, delete and update orders and we can add, remove, finish, cancel, alter the amount of a product, get the products exists in the order, get the order opened by client and get all orders exists in database.
The API content unit tests created by JUnit too, with these tests makes more easy to test all funcionalities.

# If use Eclipse to make test

If you decide to use the Eclipse IDE to make the tests, first is necessary downloading the Lombok, becouse I decide to use it to make easier use getters and setters.
You can download it in the website https://projectlombok.org/download

After download it you need to import the project as Maven Project and the API will be ready to test.

# If use just the command line

if you decide to use only the command line, just use the following command line:
mvn spring-boot:run

For more information, I included in the repository a power point that has all implementation like a flow. The archive name is Atividades Teste Brasilprev.pptx
