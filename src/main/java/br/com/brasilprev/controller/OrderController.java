package br.com.brasilprev.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brasilprev.core.domain.Order;
import br.com.brasilprev.core.domain.Product;
import br.com.brasilprev.core.exception.WarningMessageException;
import br.com.brasilprev.core.service.OrderServices;
import br.com.brasilprev.core.service.ProductServices;

@RestController
@RequestMapping("/orders")
public class OrderController {

	static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderServices orderServices;

	@Autowired
	private ProductServices productServices;

	@RequestMapping(method = POST, path = "/updateAmount/{idClient}/{idProduct}/{amount}")
	public void update(@PathVariable Long idClient, 
			@PathVariable Long idProduct,
			@PathVariable Integer amount) throws WarningMessageException {
		orderServices.updateProductAmount(idClient, idProduct, amount);
	}

	@RequestMapping(method = POST, path = "/addProduct/{idClient}/{idProduct}/{amount}")
	public Order add(@PathVariable Long idClient, 
			@PathVariable Long idProduct, 
			@PathVariable Integer amount) throws WarningMessageException {
		return orderServices.addProduct(idClient, idProduct, amount);
	}

	@RequestMapping(method = POST, path = "/removeProduct/{idClient}/{idProduct}")
	public Order remove(@PathVariable Long idClient, 
			@PathVariable Long idProduct) throws WarningMessageException {
		return orderServices.removeProduct(idClient, idProduct);
	}

	@RequestMapping(method = POST, path = "/finish/{idOrder}")
	public Order finish(@PathVariable Long idOrder) throws WarningMessageException {
		return orderServices.finish(idOrder);
	}
	
	@RequestMapping(method = POST, path = "/cancel/{idOrder}")
	public Order cancel(@PathVariable Long idOrder) throws WarningMessageException {
		return orderServices.cancel(idOrder);
	}

	@RequestMapping(value = "/openOrder/{idClient}/client", method = GET)
	public Order get(@PathVariable Long idClient) {
		return orderServices.findOneByClientAndOpenedStatus(idClient);
	}

	@RequestMapping(value = "/{idClient}/client", method = GET)
	public List<Order> getAllByClient(@PathVariable Long idClient) {
		return orderServices.findByClient(idClient);
	}

	@RequestMapping(value = "/{id}", method = GET)
	public Order getOneOrder(@PathVariable Long id) {
		return orderServices.findOne(id);
	}

	@RequestMapping(value = "/{idOrder}/products", method = GET)
	public List<Product> findByorder(@PathVariable Long idOrder) {
		return productServices.findByOrder(idOrder);
	}

}
