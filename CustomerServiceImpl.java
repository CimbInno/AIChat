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

import com.cimb.chatbot.dao.CustomerDAO;
import com.cimb.chatbot.dao.CustomerDAOImpl;
import com.cimb.chatbot.model.Customer;
import com.cimb.chatbot.model.Transaction;
import com.cimb.chatbot.model.Account;



@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService{

    @Resource(name="customerDao")
	public CustomerDAO  customerDao;
   
    
    public void save(Customer cust){
    	customerDao.save(cust);
    }
    

	public String getResponse(Customer customer,int id) {
		String response = customerDao.getResponse(customer,id);
		return response;
	}


	public int getAccount(String type, int custid) {
		int accountnum = customerDao.getAccount(type, custid);
		return accountnum;
	}

	public double getBalance(String type, int custid) {
		double balance = customerDao.getBalance(type, custid);
		return balance;
	}


	public List<Account> getAccounts(int custid) {
		List<Account> accList = customerDao.getAccounts(custid);
		return accList;
	}


	public boolean transferAmount(Transaction transaction) {
        boolean flag =  customerDao.transferAmount(transaction);
		return flag;
	}
	
}
