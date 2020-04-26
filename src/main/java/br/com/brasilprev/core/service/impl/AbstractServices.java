package br.com.brasilprev.core.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.brasilprev.core.domain.User;
import br.com.brasilprev.core.exception.WarningMessageException;
import br.com.brasilprev.core.util.DAO;
import br.com.brasilprev.core.util.IDomain;

@Service
@Transactional
public abstract class AbstractServices<T extends IDomain> {

    public abstract Class<T> getEntityClass();
    
	private DAO<T> dao;
	
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
    	dao = new DAO<T>(entityManager, getEntityClass());
    }
    
    public DAO<T> getDao() {
    	return dao;
    }
    
    protected User getUsuarioLogged() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if(auth.getPrincipal() instanceof User)
        	return (User) auth.getPrincipal();
        
        // else (String como "anonymousUser")
		return null;
    }
    
    protected T save(CrudRepository<T, Long> repository, T obj) throws WarningMessageException {
		T objSaved = repository.save(obj);
		return objSaved;
		
    }
    
	protected void delete(CrudRepository<T, Long> repository, Long id) throws WarningMessageException {
		repository.delete(id);
	}

    
}
