package com.cimb.chatbot.dao;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cimb.chatbot.model.Customer;
import com.cimb.chatbot.model.History;


@Repository("historyDao")
@Transactional
public class HistoryDAOImpl extends AbstractDao implements HistoryDAO {

	public void save(History history) {
		// TODO Auto-generated method stub
		Transaction tx =getSession().beginTransaction();
		getSession().save(history);
    	tx.commit();
    	getSession().close();
	}

	public void update(History history) {
		// TODO Auto-generated method stub
		String response = history.getResponse();
		int id = history.getId();
		Transaction tx =getSession().beginTransaction();
		Query query = getSession().createQuery("update com.cimb.chatbot.model.History  set response=:resp  where id=:id");
		query.setParameter("resp", response);
		query.setParameter("id", id);
		query.executeUpdate();
		tx.commit();
		getSession().flush();
		getSession().close();
	}

	public void delete(History history) {
		// TODO Auto-generated method stub
		
	}

	public History list(int id) {
			Criteria criteria = getSession().createCriteria(History.class);
			criteria.add(Restrictions.eq("id",id));
			History history =(History)criteria.uniqueResult();
    		return history;
    	
	}

	@SuppressWarnings("unchecked")
	public int getPreviousIntentId() {
		List<History> history = getSession().createQuery("from com.cimb.chatbot.model.History where id =(select max(id) from History)").list();
		int prevIntentId = 0;	
		if(!history.isEmpty())
			prevIntentId = history.get(0).getIntentid();
		return prevIntentId;
	}

	public String getProductName() {
		List<History> history = getSession().createQuery("from com.cimb.chatbot.model.History where id =(select max(id) from History)").list();
		String productName = null;	
		if(!history.isEmpty())
			productName = history.get(0).getProduct();
		return productName;
	}
	
}