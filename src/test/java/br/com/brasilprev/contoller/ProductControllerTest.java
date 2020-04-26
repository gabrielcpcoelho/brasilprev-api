package br.com.brasilprev.contoller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.brasilprev.AbstractTest;
import br.com.brasilprev.TestUtil;
import br.com.brasilprev.core.domain.Product;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductControllerTest extends AbstractTest {

	private Product getNewObj() {
		Product obj = new Product();
		obj.setCode("777");
		obj.setDescription("Nike Shoes 44");
		obj.setName("Nike Shoes");
		obj.setPrice(9.9D);
		return obj;
	}

	/**
	 * This test is responsible to create a new product in the database.
	 * @throws Exception
	 */
	@Test
	public void test1ShouldAddAProduct() throws Exception {

		String product = mockMvc.perform(get("/products?code=777")).andReturn().getResponse().getContentAsString();
		if(product == null || product.equals("")) {
			Product expected = getNewObj();
			String json = TestUtil.convertObjectToJson(expected);
			product = mockMvc.perform(post("/products")
					.contentType(APPLICATION_JSON_UTF8)
					.content(json))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id", notNullValue()))
					.andExpect(jsonPath("$.code", is(expected.getCode())))
					.andReturn().getResponse().getContentAsString();
		} else {
			Product expected = getNewObj();
			expected.setCode("998");
			String json = TestUtil.convertObjectToJson(expected);
			product = mockMvc.perform(post("/products")
					.contentType(APPLICATION_JSON_UTF8)
					.content(json))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id", notNullValue()))
					.andExpect(jsonPath("$.code", is(expected.getCode())))
					.andReturn().getResponse().getContentAsString();
		}

	}

	/**
	 * This test is responsible to update a product in the database.
	 * @throws Exception
	 */
	@Test
	public void test2ShouldUpdateAProduct() throws Exception {

		String product = mockMvc.perform(get("/products?code=777")).andReturn().getResponse().getContentAsString();
		if(product == null || product.equals("")) {
			Product expected = getNewObj();
			String json = TestUtil.convertObjectToJson(expected);
			product = mockMvc.perform(post("/products")
					.contentType(APPLICATION_JSON_UTF8)
					.content(json))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id", notNullValue()))
					.andExpect(jsonPath("$.code", is(expected.getCode())))
					.andReturn().getResponse().getContentAsString();
		}
		Product productObj = (Product) TestUtil.convertJsonToObject(product, Product.class);
		productObj.setName("Other name");
		
		String json = TestUtil.convertObjectToJson(productObj);
		mockMvc.perform(put("/products")
				.contentType(APPLICATION_JSON_UTF8)
				.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.id", is(productObj.getId().intValue())))
				.andExpect(jsonPath("$.code", is(productObj.getCode())))
				.andExpect(jsonPath("$.name", is(productObj.getName())));

	}

	/**
	 * This test is responsible to get a product in the database.
	 * @throws Exception
	 */	
	@Test
	public void test3ShouldGetAProduct() throws Exception {

		String product = mockMvc.perform(get("/products?code=777")).andReturn().getResponse().getContentAsString();
		if(product == null || product.equals("")) {
			Product expected = getNewObj();
			String json = TestUtil.convertObjectToJson(expected);
			product = mockMvc.perform(post("/products")
					.contentType(APPLICATION_JSON_UTF8)
					.content(json))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id", notNullValue()))
					.andExpect(jsonPath("$.code", is(expected.getCode())))
					.andReturn().getResponse().getContentAsString();
		}
		Product productObj = (Product) TestUtil.convertJsonToObject(product, Product.class);
		
		mockMvc.perform(get("/products/" + productObj.getId())
				.contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.id", is(productObj.getId().intValue())));

	}

	/**
	 * This test is responsible to remove a product in the database.
	 * @throws Exception
	 */
	@Test
	public void test4ShouldRemoveAProduct() throws Exception {

		String product = mockMvc.perform(get("/products?code=777")).andReturn().getResponse().getContentAsString();
		if(product == null || product.equals("")) {
			Product expected = getNewObj();
			String json = TestUtil.convertObjectToJson(expected);
			product = mockMvc.perform(post("/products")
					.contentType(APPLICATION_JSON_UTF8)
					.content(json))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id", notNullValue()))
					.andExpect(jsonPath("$.code", is(expected.getCode())))
					.andReturn().getResponse().getContentAsString();
		}
		Product productObj = (Product) TestUtil.convertJsonToObject(product, Product.class);
		
		mockMvc.perform(delete("/products/" + productObj.getId())
				.contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
		
	}

}
