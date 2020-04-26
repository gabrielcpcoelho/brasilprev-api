package br.com.brasilprev.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.brasilprev.core.domain.Client;
import br.com.brasilprev.core.domain.Order;
import br.com.brasilprev.core.domain.OrderProductClient;
import br.com.brasilprev.core.domain.Product;
import br.com.brasilprev.core.exception.WarningMessageException;
import br.com.brasilprev.core.repository.OrderProductClientRepository;
import br.com.brasilprev.core.service.OrderProductClientServices;

@Service
@Transactional
public class OrderProductClientServicesImpl extends AbstractServices<OrderProductClient> implements OrderProductClientServices {

	static final Logger LOG = LoggerFactory.getLogger(OrderProductClientServicesImpl.class);
	
    @Autowired
    private OrderProductClientRepository orderProductClientRepository;

    @Override
    public Class<OrderProductClient> getEntityClass() {
    	return OrderProductClient.class;
    }
    
	@Override
	public List<OrderProductClient> find(Order order, Product product, Client client) {
		
		Map<String, Object> values = new HashMap<String, Object>();
		String sql = "select distinct p from OrderProductClient p where 1=1 ";

		if(order != null && order.getId() != null) {
			sql += " and p.order.id = :orderParam ";
			values.put("orderParam", order.getId());
		}

		if(product != null && product.getId() != null) {
			sql += " and p.product.id = :productParam ";
			values.put("productParam", product.getId());
		}

		if(client != null && client.getId() != null) {
			sql += " and p.client.id = :clientParam ";
			values.put("clientParam", client.getId());
		}

		TypedQuery<OrderProductClient> query = getDao().createTypedQuery(sql);
		for(String key : values.keySet()) {
			query.setParameter(key, values.get(key));
		}
		
		List<OrderProductClient> itera = query.getResultList();
		return itera;
	}

	@Override
	public OrderProductClient findOne(Long id) {
		OrderProductClient obj = orderProductClientRepository.findOne(id);
		return obj;
	}

	@Transactional
	@Override
	public OrderProductClient save(OrderProductClient obj) throws WarningMessageException {
		
		// verificar campos obrigatórios
		if(obj == null)
			throw new WarningMessageException("OrderProductClient is required.");
		if(obj.getClient() == null || obj.getClient().getId() == null)
			throw new WarningMessageException("Client is required.");
		if(obj.getProduct() == null || obj.getProduct().getId() == null)
			throw new WarningMessageException("Product is required.");
		if(obj.getOrder() == null || obj.getOrder().getId() == null)
			throw new WarningMessageException("Order is required.");

		return save(orderProductClientRepository, obj);
	}
	
	@Override
	public void delete(Long id) throws WarningMessageException {
		delete(orderProductClientRepository, id);
	}

}
