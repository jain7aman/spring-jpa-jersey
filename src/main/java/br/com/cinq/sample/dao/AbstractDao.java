package br.com.cinq.sample.dao;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Cache;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao<PK extends Serializable, T> {

	private final Class<T> persistentClass;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public AbstractDao() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	protected Session getSession() {
		Session session =  sessionFactory.getCurrentSession();
		if (session != null) {
		    session.clear(); // internal cache clear
		}
		
		Cache cache = sessionFactory.getCache();

		if (cache != null) {
		    cache.evictAllRegions(); // Evict data from all query regions.
		}
		return session;
	}
	
	@SuppressWarnings("unchecked")
	public T getByKey(PK key) {
		return (T) getSession().get(persistentClass, key);
	}
	
	public void persist(T entity) {
		getSession().persist(entity);
	}
	
	public void save(T entity) {
		getSession().save(entity);
	}
	
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}
	
	public void delete(T entity) {
		getSession().delete(entity);
	}
	
	protected Criteria createEntityCriteria() {
		 return getSession().createCriteria(persistentClass);
	}
}
