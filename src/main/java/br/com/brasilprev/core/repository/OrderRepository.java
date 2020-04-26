package br.com.brasilprev.core.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.brasilprev.core.domain.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	
}
