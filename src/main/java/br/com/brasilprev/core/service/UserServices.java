package br.com.brasilprev.core.service;

import java.util.List;

import br.com.brasilprev.core.domain.User;
import br.com.brasilprev.core.exception.WarningMessageException;

public interface UserServices {

	public List<User> find(String cpf, String name, String email);

	public User findOne(Long id);

	public User save(User user) throws WarningMessageException; 

	public void delete(Long id) throws WarningMessageException;

}
