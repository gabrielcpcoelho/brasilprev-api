package br.com.brasilprev.core.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.brasilprev.core.domain.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

	public Client findByCpfAndNameAndEmail(String cpf, String name, String email);

	public Client findOneByCpf(String cpf);

}
