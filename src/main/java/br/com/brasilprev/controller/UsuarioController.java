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

import br.com.brasilprev.core.domain.User;
import br.com.brasilprev.core.exception.WarningMessageException;
import br.com.brasilprev.core.service.UserServices;

@RestController
@RequestMapping("/users")
public class UsuarioController {

	static final Logger LOG = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private UserServices userServices;

	@RequestMapping(params = { "cpf", "name", "email" }, method = GET)
	public List<User> search(@RequestParam("cpf") String cpf,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		return userServices.find(cpf, name, email);
	}

	@RequestMapping(value = "/{id}", method = GET)
	public User get(@PathVariable Long id) {
		return userServices.findOne(id);
	}

	@RequestMapping(method = POST)
	public User add(@RequestBody User usuario) throws WarningMessageException {
		return userServices.save(usuario);
	}

	@RequestMapping(method = PUT)
	public User update(@RequestBody User usuario) throws WarningMessageException {
		return userServices.save(usuario);
	}

	@RequestMapping(value = "/{id}", method = DELETE)
	public void delete(@PathVariable Long id) throws WarningMessageException {
		userServices.delete(id);
	}

}
