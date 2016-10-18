package com.cimb.chatbot.service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cimb.chatbot.dao.HistoryDAO;
import com.cimb.chatbot.model.Customer;
import com.cimb.chatbot.model.History;

@Service("historyService")
@Transactional
public class HistoryServiceImpl implements HistoryService{

    @Resource(name="historyDao")
	public HistoryDAO  historyDao;
   
    
    public void save(History history) {
		historyDao.save(history);
		
	}


    public History list(int id){
    	History history =historyDao.list(id);
    	return history;
    }


	public void update(History history) {
		historyDao.update(history);
	}
	
	public int getPreviousIntentId() {
		int intentId =historyDao.getPreviousIntentId();
		return intentId;
	}


	public String getProductName() {
		String productName = historyDao.getProductName();
		return productName;
	}

}