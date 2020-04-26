package br.com.brasilprev.core.service;

import java.util.List;

import br.com.brasilprev.core.domain.Client;
import br.com.brasilprev.core.exception.WarningMessageException;

public interface ClientServices {

	public List<Client> find(String cpf, String name, String email);

	public Client findOneByCpf(String cpf);

	public Client findOne(Long id);

	public Client save(Client client) throws WarningMessageException; 

	public void delete(Long id) throws WarningMessageException;

}
