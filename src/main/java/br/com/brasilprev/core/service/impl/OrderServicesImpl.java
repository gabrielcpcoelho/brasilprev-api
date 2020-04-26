package br.com.brasilprev.core.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.brasilprev.core.constants.StatusEnum;
import br.com.brasilprev.core.domain.Client;
import br.com.brasilprev.core.domain.Order;
import br.com.brasilprev.core.domain.OrderProductClient;
import br.com.brasilprev.core.domain.Product;
import br.com.brasilprev.core.exception.WarningMessageException;
import br.com.brasilprev.core.repository.OrderRepository;
import br.com.brasilprev.core.service.ClientServices;
import br.com.brasilprev.core.service.OrderProductClientServices;
import br.com.brasilprev.core.service.OrderServices;
import br.com.brasilprev.core.service.ProductServices;

@Service
@Transactional
public class OrderServicesImpl extends AbstractServices<Order> implements OrderServices {

	static final Logger LOG = LoggerFactory.getLogger(OrderServicesImpl.class);
	
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductClientServices orderProductClientServices;

    @Autowired
    private ProductServices productServices;

    @Autowired
    private ClientServices clientServices;

    @Override
    public Class<Order> getEntityClass() {
    	return Order.class;
    }
    
	@Override
	public List<Order> findByClient(Long id) {
		
		Map<String, Object> values = new HashMap<String, Object>();
		String sql = "select distinct o from Order o, OrderProductClient opc where opc.order.id = o.id ";

		if(id != null) {
			sql += " and opc.client.id = :id ";
			values.put("id", id);
		}

		sql += " order by o.date ";

		TypedQuery<Order> query = getDao().createTypedQuery(sql);
		for(String key : values.keySet()) {
			query.setParameter(key, values.get(key));
		}
		
		List<Order> itera = query.getResultList();
		return itera;
	}

	@Override
	public Order findOne(Long id) {
		Order obj = orderRepository.findOne(id);
		return obj;
	}

	@Transactional
	private Order save(Order obj) throws WarningMessageException {
		
		// verificar campos obrigatórios
		if(obj == null)
			throw new WarningMessageException("Order is required.");
		if(obj.getStatus() == null)
			throw new WarningMessageException("Status is required.");
		if(obj.getDate() == null)
			throw new WarningMessageException("Date is required.");

		return save(orderRepository, obj);
	}
	
	@Override
	public Order findOneByClientAndOpenedStatus(Long idClient) {
		Map<String, Object> values = new HashMap<String, Object>();
		String sql = "select distinct o from Order o, OrderProductClient opc where opc.order.id = o.id ";

		if(idClient != null) {
			sql += " and opc.client.id = :idClient ";
			values.put("idClient", idClient);
		}
		
		sql += " and o.status = '" + StatusEnum.OPENED + "'";
		
		sql += " order by o.date ";

		TypedQuery<Order> query = getDao().createTypedQuery(sql);
		for(String key : values.keySet()) {
			query.setParameter(key, values.get(key));
		}
		
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void updateProductAmount(Long idClient, Long idProduct, Integer amount) throws WarningMessageException {
		
		if(idClient == null)
			throw new WarningMessageException("idClient is required.");
		if(idProduct == null)
			throw new WarningMessageException("idProduct is required.");
		if(amount == null)
			throw new WarningMessageException("Amount is required.");
		
		
		Product product = productServices.findOne(idProduct);

		if(product == null)
			throw new WarningMessageException("idProduct not found.");

		Client client = clientServices.findOne(idClient);

		if(client == null)
			throw new WarningMessageException("idClient not found.");

		Order order = findOneByClientAndOpenedStatus(idClient);
		
		if(order != null) {

			List<OrderProductClient> list = orderProductClientServices.find(order, product, client);
			OrderProductClient obj = list.get(0);
			if(obj != null) {
				obj.setAmount(amount);
				orderProductClientServices.save(obj);
			}

		}
		
	}

	@Override
	public Order cancel(Long id) throws WarningMessageException  {
		
		if(id == null)
			throw new WarningMessageException("Order Id is required.");
		
		Order order = orderRepository.findOne(id);
		if(order != null) {
			order.setStatus(StatusEnum.CANCELED);
			orderRepository.save(order);
		}
		
		return order;
	}

	@Override
	public Order finish(Long id) throws WarningMessageException {
		
		if(id == null)
			throw new WarningMessageException("Order Id is required.");
		
		Order order = orderRepository.findOne(id);
		if(order != null) {
			order.setStatus(StatusEnum.FINISHED);
			orderRepository.save(order);
		}
		
		return order;
	}

	@Override
	public Order addProduct(Long idClient, Long idProduct, Integer amount) throws WarningMessageException {

		if(idClient == null)
			throw new WarningMessageException("Client Id is required.");
		if(idProduct == null)
			throw new WarningMessageException("Product Id is required.");
		if(amount == null || amount <= 0)
			throw new WarningMessageException("Amount is required.");
		
		Product product = productServices.findOne(idProduct);
		
		if(product == null)
			throw new WarningMessageException("Product Id " + idProduct + " not found.");

		Client client = clientServices.findOne(idClient);

		if(client == null)
			throw new WarningMessageException("Client Id " + idClient + " not found.");

		Order order = findOneByClientAndOpenedStatus(idClient);
		if(order == null) {
			
			order = new Order();
			order.setDate(new Date());
			order.setStatus(StatusEnum.OPENED);
			orderRepository.save(order);
			
		}
		
		OrderProductClient orderProductClient = null;
		
		List<OrderProductClient> oList = orderProductClientServices.find(order, product, client);
		if(oList != null && !oList.isEmpty()) {
			orderProductClient = oList.get(0);
		} else {
			orderProductClient = new OrderProductClient();
			orderProductClient.setAmount(0);
			orderProductClient.setClient(client);
			orderProductClient.setOrder(order);
			orderProductClient.setPrice(product.getPrice());
			orderProductClient.setProduct(product);
		}
		orderProductClient.setAmount(orderProductClient.getAmount() + amount);
		
		orderProductClientServices.save(orderProductClient);
		return order;
	}

	@Override
	public Order removeProduct(Long idClient, Long idProduct) throws WarningMessageException {

		if(idClient == null)
			throw new WarningMessageException("Client Id is required.");
		if(idProduct == null)
			throw new WarningMessageException("Product Id is required.");
		
		Product product = productServices.findOne(idProduct);
		
		if(product == null)
			throw new WarningMessageException("Product Id " + idProduct + " not found.");

		Client client = clientServices.findOne(idClient);

		if(client == null)
			throw new WarningMessageException("Client Id " + idClient + " not found.");

		Order order = findOneByClientAndOpenedStatus(idClient);
		if(order != null) {
			List<OrderProductClient> oList = orderProductClientServices.find(order, product, client);
			OrderProductClient orderProductClient = oList.get(0);
			orderProductClientServices.delete(orderProductClient.getId());
		}
		
		return order;
	}
}
