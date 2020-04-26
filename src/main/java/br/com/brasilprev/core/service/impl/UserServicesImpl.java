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

import br.com.brasilprev.core.domain.User;
import br.com.brasilprev.core.exception.WarningMessageException;
import br.com.brasilprev.core.repository.UserRepository;
import br.com.brasilprev.core.service.UserServices;

@Service
@Transactional
public class UserServicesImpl extends AbstractServices<User> implements UserServices {

	static final Logger LOG = LoggerFactory.getLogger(UserServicesImpl.class);
	
    @Autowired
    private UserRepository userRepository;

    @Override
    public Class<User> getEntityClass() {
    	return User.class;
    }
    
	@Override
	public List<User> find(String cpf, String name, String email) {
		
		Map<String, Object> values = new HashMap<String, Object>();
		String sql = "select u from User u where 1=1 ";

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

		TypedQuery<User> query = getDao().createTypedQuery(sql);
		for(String key : values.keySet()) {
			query.setParameter(key, values.get(key));
		}
		
		List<User> itera = query.getResultList();
		return itera;
	}

	@Override
	public User findOne(Long id) {
		User obj = userRepository.findOne(id);
		return obj;
	}

	@Transactional
	@Override
	public User save(User obj) throws WarningMessageException {
		
		// verificar campos obrigatórios
		if(obj == null)
			throw new WarningMessageException("User is required.");
		if(obj.getCpf() == null)
			throw new WarningMessageException("CPF is required.");
		if(obj.getName() == null)
			throw new WarningMessageException("Name is required.");
		if(obj.getEmail() == null)
			throw new WarningMessageException("Email is required.");
		if(obj.getPassword() == null)
			throw new WarningMessageException("Password is required.");

		// verificar duplicidade
		List<User> objetos = userRepository.findByCpf(obj.getCpf());
		if(objetos != null && !objetos.isEmpty() && !objetos.get(0).getId().equals(obj.getId()))
			throw new WarningMessageException("CPF '" + obj.getCpf() + "' já cadastrado.");

		return save(userRepository, obj);
	}
	
	@Override
	public void delete(Long id) throws WarningMessageException {
		
		User user = getUsuarioLogged();
		
		if (id.equals(user.getId()))
			throw new WarningMessageException("Usuario não pode excluir a sua propria conta.");

		delete(userRepository, id);
	}

}
