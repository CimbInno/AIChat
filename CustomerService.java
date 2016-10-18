package com.cimb.chatbot.service;

import com.cimb.chatbot.model.Customer;
import com.cimb.chatbot.model.Transaction;

import java.util.List;

import com.cimb.chatbot.model.Account;

public interface CustomerService {
	 public void save(Customer cust);
	 //public void update(Customer cust);
	 //public void delete(Customer cust);
	 //public List<Customer> list();
	 public String getResponse(Customer customer,int id);
	 public int getAccount(String type,int custid);
	 public double getBalance(String type,int custid);
	 public List<Account> getAccounts(int custid);
	 public boolean transferAmount(Transaction transaction);
}
