package br.com.brasilprev.core.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.brasilprev.core.domain.OrderProductClient;

@Repository
public interface OrderProductClientRepository extends CrudRepository<OrderProductClient, Long> {
	
}
