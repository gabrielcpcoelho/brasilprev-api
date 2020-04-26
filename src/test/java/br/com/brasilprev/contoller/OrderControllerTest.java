package br.com.brasilprev.contoller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.brasilprev.AbstractTest;
import br.com.brasilprev.TestUtil;
import br.com.brasilprev.core.constants.StatusEnum;
import br.com.brasilprev.core.domain.Client;
import br.com.brasilprev.core.domain.Order;
import br.com.brasilprev.core.domain.Product;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderControllerTest extends AbstractTest {

	private Client clientCreated;
	private Product product1Created;
	private Product product2Created;
	
	private Client getNewClient() {
		Client obj = new Client();
		obj.setCpf("36414393886");
		obj.setName("Gabriel Coelho");
		obj.setEmail("gabrielcpcoelho@gmail.com");
		return obj;
	}
	
	private Product getNewProduct1(){
		Product obj = new Product();
		obj.setCode("111222");
		obj.setName("Nike Shoes");
		obj.setDescription("Nike shoes 44");
		obj.setPrice(399.9);
		return obj;
	}

	private Product getNewProduct2(){
		Product obj = new Product();
		obj.setCode("333444");
		obj.setName("Adidas Shoes");
		obj.setDescription("Adidas shoes 42");
		obj.setPrice(500.0);
		return obj;
	}

	/**
	 * This test is responsible to set the basic configuration of client and product in the database to create a order.
	 * @throws Exception
	 */
	public void setup() throws Exception {
		
		// Create Clients
		String client = mockMvc.perform(get("/clients?cpf=36414393886")).andReturn().getResponse().getContentAsString();
		if(client == null || client.equals("")) {
			Client clientObj = getNewClient();
			String clientJson = TestUtil.convertObjectToJson(clientObj);
			client = mockMvc.perform(post("/clients")
					.contentType(APPLICATION_JSON_UTF8)
					.content(clientJson))
					.andReturn().getResponse().getContentAsString();
		}
		clientCreated = (Client) TestUtil.convertJsonToObject(client, Client.class);
		
		// Create Products 1
		String product = mockMvc.perform(get("/products?code=111222")).andReturn().getResponse().getContentAsString();
		if(product == null || product.equals("")) {
			Product productObj1 = getNewProduct1();
			String productJson1 = TestUtil.convertObjectToJson(productObj1);
			product = mockMvc.perform(post("/products")
					.contentType(APPLICATION_JSON_UTF8)
					.content(productJson1))
					.andExpect(status().isOk())
					.andReturn().getResponse().getContentAsString();
		}
		product1Created = (Product) TestUtil.convertJsonToObject(product, Product.class);

		// Create Products 2
		product = mockMvc.perform(get("/products?code=333444")).andReturn().getResponse().getContentAsString();
		if(product == null || product.equals("")) {
			Product productObj2 = getNewProduct2();
			String productJson2 = TestUtil.convertObjectToJson(productObj2);
			product = mockMvc.perform(post("/products")
					.contentType(APPLICATION_JSON_UTF8)
					.content(productJson2))
					.andExpect(status().isOk())
					.andReturn().getResponse().getContentAsString();
		}
		product2Created = (Product) TestUtil.convertJsonToObject(product, Product.class);

	}

	/**
	 * This test is responsible to create a new order in the database.
	 * @throws Exception
	 */
	@Test
	public void test1ShouldHaveOpenOrder() throws Exception {
		
		setup();
		
		// Add Product to an order and Check if an order was created
		mockMvc.perform(post("/orders/addProduct/" + clientCreated.getId() + "/" + product1Created.getId() + "/1") // '/orders/addProduct/{clientId}/{productId}/{amount}'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // nothing!
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.status", is(StatusEnum.OPENED.toString())))
				.andReturn().getResponse().getContentAsString();
		
		
	}

	/**
	 * This test is responsible to add products in the database.
	 * @throws Exception
	 */
	@Test
	public void test2ShouldAddProducts() throws Exception {
		
		setup();
		
		// Add Product to an order and Check if an order was created
		String orderJson = mockMvc.perform(post("/orders/addProduct/" + clientCreated.getId() + "/" + product1Created.getId() + "/1") // '/orders/addProduct/{clientId}/{productId}/{amount}'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.status", is(StatusEnum.OPENED.toString())))
				.andReturn().getResponse().getContentAsString();
		Order order = (Order) TestUtil.convertJsonToObject(orderJson, Order.class);
		
		// Add another Product to same order and Check if the same order have two products
		mockMvc.perform(post("/orders/addProduct/" + clientCreated.getId() + "/" + product2Created.getId() + "/1") // '/orders/addProduct/{clientId}/{productId}/{amount}'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.id", equalTo(order.getId().intValue())))
				.andExpect(jsonPath("$.status", is(StatusEnum.OPENED.toString())));
		
	}

	/**
	 * This test is responsible to remove products in the database.
	 * @throws Exception
	 */
	@Test
	public void test3ShouldRemoveProducts() throws Exception {
		
		setup();

		// Add two Products
		String orderJson = mockMvc.perform(post("/orders/addProduct/" + clientCreated.getId() + "/" + product1Created.getId() + "/1") // '/orders/addProduct/{clientId}/{productId}/{amount}'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		Order order = (Order) TestUtil.convertJsonToObject(orderJson, Order.class);
		mockMvc.perform(post("/orders/addProduct/" + clientCreated.getId() + "/" + product2Created.getId() + "/1") // '/orders/addProduct/{clientId}/{productId}/{amount}'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk());
		
		// Remove a Product
		mockMvc.perform(post("/orders/removeProduct/" + clientCreated.getId() + "/" + product1Created.getId()) // '/orders/removeProduct/{clientId}/{productId}'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.id", equalTo(order.getId().intValue())))
				.andExpect(jsonPath("$.status", is(StatusEnum.OPENED.toString())))
				.andReturn().getResponse().getContentAsString();
		
		// Check if the order have only one product
		mockMvc.perform(get("/orders/" + order.getId() + "/products") // '/orders/{orderId}/products'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", equalTo(1))); // Need to have only one
		
	}

	/**
	 * This test is responsible to add products and update the amount in the database.
	 * @throws Exception
	 */
	@Test
	public void test4ShouldUpdateProducts() throws Exception {
		
		setup();

		// Add a Products
		mockMvc.perform(post("/orders/addProduct/" + clientCreated.getId() + "/" + product1Created.getId() + "/1") // '/orders/addProduct/{clientId}/{productId}/{amount}'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		// Update amount of Product
		mockMvc.perform(post("/orders/updateAmount/" + clientCreated.getId() + "/" + product1Created.getId() + "/5") // '/orders/updateAmount/{clientId}/{productId}/{amount}'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

	}
	
	/**
	 * This test is responsible to cancel an order in the database.
	 * @throws Exception
	 */
	@Test
	public void test5ShouldCancelOrder() throws Exception {
		
		setup();
		
		// Add Product to an order and Check if an order was created
		String orderJson = mockMvc.perform(post("/orders/addProduct/" + clientCreated.getId() + "/" + product1Created.getId() + "/1") // '/orders/addProduct/{clientId}/{productId}/{amount}'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.status", is(StatusEnum.OPENED.toString())))
				.andReturn().getResponse().getContentAsString();
		Order order = (Order) TestUtil.convertJsonToObject(orderJson, Order.class);
		
		mockMvc.perform(post("/orders/cancel/" + order.getId()) // '/cancel/{idOrder}'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.id", equalTo(order.getId().intValue())))
				.andExpect(jsonPath("$.status", is(StatusEnum.CANCELED.toString())));
		
	}

	/**
	 * This test is responsible to finish an order in the database to start checkout.
	 * @throws Exception
	 */
	@Test
	public void test6ShouldFinishOrder() throws Exception {
		
		setup();
		
		// Add Product to an order and Check if an order was created
		String orderJson = mockMvc.perform(post("/orders/addProduct/" + clientCreated.getId() + "/" + product1Created.getId() + "/1") // '/orders/addProduct/{clientId}/{productId}/{amount}'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.status", is(StatusEnum.OPENED.toString())))
				.andReturn().getResponse().getContentAsString();
		Order order = (Order) TestUtil.convertJsonToObject(orderJson, Order.class);
		
		mockMvc.perform(post("/orders/finish/" + order.getId()) // '/finish/{idOrder}'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.id", equalTo(order.getId().intValue())))
				.andExpect(jsonPath("$.status", is(StatusEnum.FINISHED.toString())));
		
	}

	/**
	 * This test is responsible to get all orders created in the database.
	 * @throws Exception
	 */
	@Test
	public void test7ShouldHaveAllOrders() throws Exception {
		
		setup();
		
		// Add Product to an order and finish
		String orderJson = mockMvc.perform(post("/orders/addProduct/" + clientCreated.getId() + "/" + product1Created.getId() + "/1") // '/orders/addProduct/{clientId}/{productId}/{amount}'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		Order order = (Order) TestUtil.convertJsonToObject(orderJson, Order.class);
		mockMvc.perform(post("/orders/finish/" + order.getId()) // '/finish/{idOrder}'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is(StatusEnum.FINISHED.toString())));
		
		
		// Add Product to an order and cancel
		orderJson = mockMvc.perform(post("/orders/addProduct/" + clientCreated.getId() + "/" + product1Created.getId() + "/1") // '/orders/addProduct/{clientId}/{productId}/{amount}'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		order = (Order) TestUtil.convertJsonToObject(orderJson, Order.class);
		mockMvc.perform(post("/orders/cancel/" + order.getId()) // '/cancel/{idOrder}'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.id", equalTo(order.getId().intValue())))
				.andExpect(jsonPath("$.status", is(StatusEnum.CANCELED.toString())));

		
		mockMvc.perform(get("/orders/" + clientCreated.getId() + "/client") // '/{idClient}/client'
				.contentType(APPLICATION_JSON_UTF8)
				.content("")) // noyhing!!!
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", greaterThanOrEqualTo(2)));
		
	}

}
