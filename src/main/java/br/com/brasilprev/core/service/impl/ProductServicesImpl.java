package br.com.brasilprev.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.brasilprev.core.constants.StatusEnum;
import br.com.brasilprev.core.domain.Product;
import br.com.brasilprev.core.exception.WarningMessageException;
import br.com.brasilprev.core.repository.ProductRepository;
import br.com.brasilprev.core.service.ProductServices;

@Service
@Transactional
public class ProductServicesImpl extends AbstractServices<Product> implements ProductServices {

	static final Logger LOG = LoggerFactory.getLogger(ProductServicesImpl.class);
	
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Class<Product> getEntityClass() {
    	return Product.class;
    }
    
	@Override
	public List<Product> find(String code, String name) {
		
		Map<String, Object> values = new HashMap<String, Object>();
		String sql = "select p from Product p where 1=1 ";

		if(StringUtils.isNotEmpty(code)) {
			sql += " and p.code like :code ";
			values.put("code", "%" + code + "%");
		}

		if(StringUtils.isNotEmpty(name)) {
			sql += " and p.name like :name ";
			values.put("name", "%" + name + "%");
		}

		sql += " order by p.name ";

		TypedQuery<Product> query = getDao().createTypedQuery(sql);
		for(String key : values.keySet()) {
			query.setParameter(key, values.get(key));
		}
		
		List<Product> itera = query.getResultList();
		return itera;
	}

	@Override
	public Product findOneByCode(String code) {
		Product obj = productRepository.findOneByCode(code);
		return obj;
	}

	@Override
	public Product findOne(Long id) {
		Product obj = productRepository.findOne(id);
		return obj;
	}

	@Transactional
	@Override
	public Product save(Product obj) throws WarningMessageException {
		
		// verificar campos obrigatórios
		if(obj == null)
			throw new WarningMessageException("Product is required.");
		if(obj.getCode() == null)
			throw new WarningMessageException("Code is required.");
		if(obj.getName() == null)
			throw new WarningMessageException("Name is required.");
		if(obj.getDescription() == null)
			throw new WarningMessageException("Description is required.");
		if(obj.getPrice() == null)
			throw new WarningMessageException("Price is required.");


		// verificar duplicidade
		Product product = productRepository.findOneByCode(obj.getCode());
		if(product != null && !product.getId().equals(obj.getId()))
			throw new WarningMessageException("CODE '" + obj.getCode() + "' already exists.");

		return save(productRepository, obj);
	}
	
	@Override
	public void delete(Long id) throws WarningMessageException {
		delete(productRepository, id);
	}

	@Override
	public List<Product> findByOrder(Long idOrder) {
		Map<String, Object> values = new HashMap<String, Object>();
		String sql = "select distinct p from Order o, OrderProductClient opc, Product p where opc.order.id = o.id and opc.product.id = p.id ";

		if(idOrder != null) {
			sql += " and o.id = :idOrder ";
			values.put("idOrder", idOrder);
		}
		
		sql += " and o.status = '" + StatusEnum.OPENED + "'";
		
		sql += " order by p.name ";

		TypedQuery<Product> query = getDao().createTypedQuery(sql);
		for(String key : values.keySet()) {
			query.setParameter(key, values.get(key));
		}
		
		return query.getResultList();
	}

}
