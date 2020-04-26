package br.com.brasilprev.core.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.brasilprev.core.domain.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

	public Product findByCodeAndName(String code, String name);

	public Product findOneByCode(String code);

}
