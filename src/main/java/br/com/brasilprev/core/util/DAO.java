package br.com.brasilprev.core.util;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class DAO<T extends IDomain> {


	private EntityManager em;
	private Class<T> entityClass;

	public DAO(final EntityManager entityManager, final Class<T> entityClass) {
		this.em = entityManager;
		this.entityClass = entityClass;
	}

	public List<T> list() throws Exception {
		throw new IllegalAccessException("method should be implemented");
	}

	public T get(Serializable id) {
        return em.find(entityClass, id);
	}

	public T add(T t) {
		em.persist(t);
		em.flush();
		return t;
	}

	public T update(T t) {
		return em.merge(t);
	}

	public void delete(T t) {
		em.remove(t);
	}

	public Query createQuery(String sql) {
		return em.createQuery(sql);
	}

	public TypedQuery<T> createTypedQuery(String sql) {
		return em.createQuery(sql, entityClass);
	}

	public Query createNativeQuery(String sql) {
		return em.createNativeQuery(sql);
	}

	/**
	 *
	 * @param namedQuery atributo <i>name</i> da NamedQuery declarada.
	 * @return
	 */
	public TypedQuery<T> createNamedQuery(String namedQuery) {
		return em.createNamedQuery(namedQuery, entityClass);
	}

}
