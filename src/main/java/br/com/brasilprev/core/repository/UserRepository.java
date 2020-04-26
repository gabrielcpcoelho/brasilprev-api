package br.com.brasilprev.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.brasilprev.core.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	public User findByEmail(String email);

	public List<User> findByCpf(String cpf);

}
