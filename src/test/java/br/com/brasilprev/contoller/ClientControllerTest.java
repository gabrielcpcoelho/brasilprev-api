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
import br.com.brasilprev.core.domain.Client;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientControllerTest extends AbstractTest {

	private Client getNewObj() {
		Client obj = new Client();
		obj.setCpf("36414393886");
		obj.setName("Gabriel Coelho");
		obj.setEmail("gabrielcpcoelho@gmail.com");
		return obj;
	}

	/**
	 * This test is responsible to create a new client in the database.
	 * @throws Exception
	 */
	@Test
	public void test1ShouldAddAnClient() throws Exception {

		// Create Client
		String client = mockMvc.perform(get("/clients?cpf=36414393886")).andReturn().getResponse().getContentAsString();
		if(client == null || client.equals("")) {
			Client clientObj = getNewObj();
			String clientJson = TestUtil.convertObjectToJson(clientObj);
			mockMvc.perform(post("/clients")
					.contentType(APPLICATION_JSON_UTF8)
					.content(clientJson))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id", notNullValue()))
					.andExpect(jsonPath("$.cpf", is(clientObj.getCpf())));
		} else {
			Client clientObj = getNewObj();
			clientObj.setCpf("36414393899");
			String clientJson = TestUtil.convertObjectToJson(clientObj);
			mockMvc.perform(post("/clients")
					.contentType(APPLICATION_JSON_UTF8)
					.content(clientJson))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id", notNullValue()))
					.andExpect(jsonPath("$.cpf", is(clientObj.getCpf())));
		}

	}

	/**
	 * This test is responsible to update an client in the database.
	 * @throws Exception
	 */
	@Test
	public void test2ShouldUpdateAnClient() throws Exception {

		String client = mockMvc.perform(get("/clients?cpf=36414393886")).andReturn().getResponse().getContentAsString();
		if(client == null || client.equals("")) {
			Client clientObj = getNewObj();
			String clientJson = TestUtil.convertObjectToJson(clientObj);
			client = mockMvc.perform(post("/clients")
					.contentType(APPLICATION_JSON_UTF8)
					.content(clientJson))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id", notNullValue()))
					.andExpect(jsonPath("$.cpf", is(clientObj.getCpf())))
					.andReturn().getResponse().getContentAsString();
		}
		Client clientObj = (Client) TestUtil.convertJsonToObject(client, Client.class);

		clientObj.setCpf("36414393886");
		clientObj.setName("Gabriel P Coelho");
		clientObj.setEmail("gabrielcpcoelho@gmail.com");
		String json = TestUtil.convertObjectToJson(clientObj);

		mockMvc.perform(put("/clients")
				.contentType(APPLICATION_JSON_UTF8)
				.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.id", is(clientObj.getId().intValue())))
				.andExpect(jsonPath("$.name", is(clientObj.getName())));

	}
	
	/**
	 * This test is responsible to get an client in the database.
	 * @throws Exception
	 */
	@Test
	public void test3ShouldGetAnClient() throws Exception {

		Client clientObj = getNewObj();
		clientObj.setCpf("12332145600");
		String clientJson = TestUtil.convertObjectToJson(clientObj);
		String client = mockMvc.perform(post("/clients")
				.contentType(APPLICATION_JSON_UTF8)
				.content(clientJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.cpf", is(clientObj.getCpf())))
				.andReturn().getResponse().getContentAsString();
		clientObj = (Client) TestUtil.convertJsonToObject(client, Client.class);

		mockMvc.perform(get("/clients/" + clientObj.getId())
				.contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.id", is(clientObj.getId().intValue())));

	}

	/**
	 * This test is responsible to remove an client in the database.
	 * @throws Exception
	 */
	@Test
	public void test4ShouldRemoveAnClient() throws Exception {

		Client clientObj = getNewObj();
		clientObj.setCpf("12332145699");
		String clientJson = TestUtil.convertObjectToJson(clientObj);
		String client = mockMvc.perform(post("/clients")
				.contentType(APPLICATION_JSON_UTF8)
				.content(clientJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.cpf", is(clientObj.getCpf())))
				.andReturn().getResponse().getContentAsString();
		clientObj = (Client) TestUtil.convertJsonToObject(client, Client.class);

		
		mockMvc.perform(delete("/clients/" + clientObj.getId())
				.contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

	}

}
