package br.com.brasilprev.core.service;

import java.util.List;

import br.com.brasilprev.core.domain.Order;
import br.com.brasilprev.core.exception.WarningMessageException;

public interface OrderServices {

	public List<Order> findByClient(Long idClient);
	
	public Order findOneByClientAndOpenedStatus(Long idClient);

	public void updateProductAmount(Long idClient, Long idProduct, Integer amount) throws WarningMessageException;
	
	public Order cancel(Long id) throws WarningMessageException;
	
	public Order finish(Long id) throws WarningMessageException;
	
	public Order addProduct(Long idClient, Long idProduct, Integer amount) throws WarningMessageException;
	
	public Order removeProduct(Long idClient, Long idProduct) throws WarningMessageException;
	
	public Order findOne(Long id);

}
