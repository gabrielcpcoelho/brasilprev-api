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

import br.com.brasilprev.core.domain.Client;
import br.com.brasilprev.core.exception.WarningMessageException;
import br.com.brasilprev.core.repository.ClientRepository;
import br.com.brasilprev.core.service.ClientServices;

@Service
@Transactional
public class ClientServicesImpl extends AbstractServices<Client> implements ClientServices {

	static final Logger LOG = LoggerFactory.getLogger(ClientServicesImpl.class);
	
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Class<Client> getEntityClass() {
    	return Client.class;
    }
    
	@Override
	public List<Client> find(String cpf, String name, String email) {
		
		Map<String, Object> values = new HashMap<String, Object>();
		String sql = "select u from Client u where 1=1 ";

		if(StringUtils.isNotEmpty(cpf)) {
			sql += " and u.cpf like :cpf ";
			values.put("cpf", "%" + cpf + "%");
		}

		if(StringUtils.isNotEmpty(name)) {
			sql += " and u.name like :name ";
			values.put("name", "%" + name + "%");
		}

		if(StringUtils.isNotEmpty(email)) {
			sql += " and u.email like :email ";
			values.put("email", "%" + email + "%");
		}

		sql += " order by u.name ";

		TypedQuery<Client> query = getDao().createTypedQuery(sql);
		for(String key : values.keySet()) {
			query.setParameter(key, values.get(key));
		}
		
		List<Client> itera = query.getResultList();
		return itera;
	}

	@Override
	public Client findOneByCpf(String cpf) {
		Client obj = clientRepository.findOneByCpf(cpf);
		return obj;
	}

	@Override
	public Client findOne(Long id) {
		Client obj = clientRepository.findOne(id);
		return obj;
	}

	@Transactional
	@Override
	public Client save(Client obj) throws WarningMessageException {
		
		// verificar campos obrigatórios
		if(obj == null)
			throw new WarningMessageException("Client is required.");
		if(obj.getCpf() == null)
			throw new WarningMessageException("CPF is required.");
		if(obj.getName() == null)
			throw new WarningMessageException("Name is required.");
		if(obj.getEmail() == null)
			throw new WarningMessageException("Email is required.");

		// verificar duplicidade
		Client client = clientRepository.findOneByCpf(obj.getCpf());
		if(client != null && !client.getId().equals(obj.getId()))
			throw new WarningMessageException("CPF '" + obj.getCpf() + "' already exists.");

		return save(clientRepository, obj);
	}
	
	@Override
	public void delete(Long id) throws WarningMessageException {
		delete(clientRepository, id);
	}

}
