package br.com.brasilprev.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.brasilprev.core.domain.Client;
import br.com.brasilprev.core.exception.WarningMessageException;
import br.com.brasilprev.core.service.ClientServices;

@RestController
@RequestMapping("/clients")
public class ClientController {

	static final Logger LOG = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private ClientServices clientServices;

	@RequestMapping(method = GET)
	public List<Client> searchAll() {
		return clientServices.find(null, null, null);
	}

	@RequestMapping(params = { "cpf", "name", "email" }, method = GET)
	public List<Client> search(@RequestParam("cpf") String cpf,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		return clientServices.find(cpf, name, email);
	}

	@RequestMapping(params = { "cpf" }, method = GET)
	public Client searchByCpf(@RequestParam("cpf") String cpf) {
		return clientServices.findOneByCpf(cpf);
	}

	@RequestMapping(value = "/{id}", method = GET)
	public Client get(@PathVariable Long id) {
		return clientServices.findOne(id);
	}

	@RequestMapping(method = POST)
	public Client add(@RequestBody Client client) throws WarningMessageException {
		return clientServices.save(client);
	}

	@RequestMapping(method = PUT)
	public Client update(@RequestBody Client client) throws WarningMessageException {
		return clientServices.save(client);
	}

	@RequestMapping(value = "/{id}", method = DELETE)
	public void delete(@PathVariable Long id) throws WarningMessageException {
		clientServices.delete(id);
	}

}
