package com.cimb.chatbot.dao;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao {

	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	private Session session;

	public void setSessionFactory(SessionFactory sf)
	{
	    this.sessionFactory = sf;
	}
	
	protected Session getSession() {
		try 
		{
			    session = sessionFactory.getCurrentSession();
		}catch (HibernateException e) 
		{
		    //Step-3: Implementation
		    session = sessionFactory.openSession();
		}
		return session;
	}
	
	
	/*@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	

	public void persist(Object entity) {
		getSession().persist(entity);
	}

	public void delete(Object entity) {
		getSession().delete(entity);
	}*/
}
