package br.com.brasilprev.core.service;

import java.util.List;

import br.com.brasilprev.core.domain.Product;
import br.com.brasilprev.core.exception.WarningMessageException;

public interface ProductServices {

	public List<Product> find(String code, String name);

	public Product findOneByCode(String code);

	public Product findOne(Long id);

	public Product save(Product product) throws WarningMessageException; 

	public void delete(Long id) throws WarningMessageException;

	public List<Product> findByOrder(Long idOrder);

}
