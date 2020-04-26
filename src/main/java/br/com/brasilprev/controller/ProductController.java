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

import br.com.brasilprev.core.domain.Product;
import br.com.brasilprev.core.exception.WarningMessageException;
import br.com.brasilprev.core.service.ProductServices;

@RestController
@RequestMapping("/products")
public class ProductController {

	static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductServices productServices;

	@RequestMapping(params = { "code", "name" }, method = GET)
	public List<Product> search(@RequestParam("code") String code,
			@RequestParam("name") String name) {
		return productServices.find(code, name);
	}

	@RequestMapping(params = { "code" }, method = GET)
	public Product search(@RequestParam("code") String code) {
		return productServices.findOneByCode(code);
	}

	@RequestMapping(value = "/{id}", method = GET)
	public Product get(@PathVariable Long id) {
		return productServices.findOne(id);
	}

	@RequestMapping(method = POST)
	public Product add(@RequestBody Product product) throws WarningMessageException {
		return productServices.save(product);
	}

	@RequestMapping(method = PUT)
	public Product update(@RequestBody Product product) throws WarningMessageException {
		return productServices.save(product);
	}

	@RequestMapping(value = "/{id}", method = DELETE)
	public void delete(@PathVariable Long id) throws WarningMessageException {
		productServices.delete(id);
	}

}
