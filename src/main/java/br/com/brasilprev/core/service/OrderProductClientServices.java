package br.com.brasilprev.core.service;

import java.util.List;

import br.com.brasilprev.core.domain.Client;
import br.com.brasilprev.core.domain.Order;
import br.com.brasilprev.core.domain.OrderProductClient;
import br.com.brasilprev.core.domain.Product;
import br.com.brasilprev.core.exception.WarningMessageException;

public interface OrderProductClientServices {

	public List<OrderProductClient> find(Order order, Product product, Client client);

	public OrderProductClient findOne(Long id);

	public OrderProductClient save(OrderProductClient orderProductClient) throws WarningMessageException; 

	public void delete(Long id) throws WarningMessageException;

}
