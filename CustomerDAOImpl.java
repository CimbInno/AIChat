package com.cimb.chatbot.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;


import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.cimb.chatbot.model.Account;
import com.cimb.chatbot.model.Airasia;
import com.cimb.chatbot.model.Customer;
import com.cimb.chatbot.model.Kwik;
import com.cimb.chatbot.model.MasterCard;
import com.cimb.chatbot.text.speech.SpeechCore;

@Repository("customerDao")
@Transactional
public class CustomerDAOImpl extends AbstractDao implements CustomerDAO {


	
	 public String getResponse(Customer customer,int id) {
		String response=null;
		
		/*SpeechCore sc = new SpeechCore();
		String output =null;
		try{
			output =sc.normal("Mary","chase","the monkey");
			System.out.println("----nlg output---"+ output );
		
		}catch(Exception ex){
			System.out.println("----Exception occured---"+ex.getMessage());
		}*/
		
		if(id ==0){
			int randomNo = getRandomNumberInRange(501, 505);
			 response =getKeyValue(Integer.toString(randomNo));
        	//response = "Sorry, this Bot deals with CIMB products Only !!! ";
        }
		
		if(id >=1 && id <=6){
			Criteria criteria = getSession().createCriteria(Kwik.class);
	    	criteria.add(Restrictions.eq("custid",customer.getId()));
	    /*	Kwik kwik =(Kwik)criteria.uniqueResult();*/
	       
	    	criteria.addOrder(Order.desc("txdate"));
	    	List<Kwik> kwikList =criteria.list();
	    	Kwik kwik =(Kwik)kwikList.get(0);
	    	System.out.println("----Kwik Object----"+kwik);
	    	double balance  = kwik.getBalance();
	    	String  last_transaction  = kwik.getTxdetail();
	    	double last_transaction_amount  = kwik.getTxamount();
	    	Date last_transaction_date  = kwik.getTxdate();
	    	int accountnum  = kwik.getAccountnumber();
	    	String recent_transaction  = kwik.getTxdetail();
	    	
	    	
	    	
	    	
	    	final String EXPECTED_DATE_FORMAT = "yyyy-MM-dd";
	    	String newDateString = null;
            try {
            //	String str = "2016-06-15 00:00:00.0";
            	String str = last_transaction_date.toString();
        		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        		Date date = formatter.parse(str);
        		newDateString = new java.text.SimpleDateFormat(EXPECTED_DATE_FORMAT).format(date);
        	//	response = "Your Kwik Account Last Transaction Date is "+newDateString;
            //	System.out.println("Your Kwik Account Last Transaction Date is "+newDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
	        
            
            
	       
	        if(id ==1){
	        	response = "Your Kwik Account Balance is "+Double.toString(balance);
	        }
	    	
	        if(id ==2){
	        	response = "Your Kwik Account Last Transaction is "+last_transaction+" on "+newDateString+" with the purchase amount of "+last_transaction_amount;
	        }
	    	
	        if(id ==3){
	        	response ="Your Kwik Account Last Transaction amount is "+Double.toString(last_transaction_amount);
	        }
	    	
	        if(id ==4){
	       
	        /*	final String EXPECTED_DATE_FORMAT = "yyyy-MM-dd";
	    		
	            try {
	            //	String str = "2016-06-15 00:00:00.0";
	            	String str = last_transaction_date.toString();
	            	System.out.println("--str---"+str);
	        		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	        		Date date = formatter.parse(str);
	        		String newDateString = new java.text.SimpleDateFormat(EXPECTED_DATE_FORMAT).format(date);
	        		response = "Your Kwik Account Last Transaction Date is "+newDateString;
	            	System.out.println("Your Kwik Account Last Transaction Date is "+newDateString);
	            } catch (ParseException e) {
	                e.printStackTrace();
	            }*/
	        	response = "Your Kwik Account Last Transaction Date is "+newDateString;
	        	
	        }
	        
	       if(id ==5){
	        	response ="Your Kwik Account Number is "+accountnum;
	        }
	        
	        if(id ==6){
	        	//response ="Your Kwik Account recent Transactions is "+recent_transaction;
		    //	criteria.add(Restrictions.eq("custid",customer.getId()));
		    //	criteria.addOrder(Order.desc("txdate"));
		    //	List<Kwik> kwikList =criteria.list();
		    	String recent_transactions="";
		    	recent_transactions+="S.No		"+"Transaction		"+"Amount		"+"Date"+"\n";
		    	for(int i=0 ; i < kwikList.size();i++){
		    		System.out.println("--Transaction---"+kwikList.get(i).getTxdetail()+"--Amount Spent---"+kwikList.get(i).getTxamount()+"---Date---"+getTransactionDate(kwikList.get(i).getTxdate()));
		    		recent_transactions +=i+1 +"			"+kwikList.get(i).getTxdetail()+"			"+kwikList.get(i).getTxamount()+"			"+ getTransactionDate(kwikList.get(i).getTxdate())+"\n";
		    	}
		    	response ="Your Kwik Account recent Transactions are below: "+"\n"+recent_transactions;
	        }
		}
		
		
		if(id >=7 && id <=12){
			Criteria criteria = getSession().createCriteria(Airasia.class);
	    	criteria.add(Restrictions.eq("custid",customer.getId()));
	    //	Airasia airasia =(Airasia)criteria.uniqueResult();
	    	
	      	criteria.addOrder(Order.desc("txdate"));
	    	List<Airasia> airasiaList =criteria.list();
	    	Airasia airasia =(Airasia)airasiaList.get(0);
	    	
	    	
	    	System.out.println("----airasia Object----"+airasia);
	    	double balance  = airasia.getBalance();
	    	String  last_transaction  = airasia.getTxdetail();
	    	double last_transaction_amount  = airasia.getTxamount();
	    	Date last_transaction_date  = airasia.getTxdate();
	    	int accountnum  = airasia.getAccountnumber();
	    	String recent_transaction  = airasia.getTxdetail();
	    	
	    	final String EXPECTED_DATE_FORMAT = "yyyy-MM-dd";
	    	String newDateString = null;
            try {
            //	String str = "2016-06-15 00:00:00.0";
            	String str = last_transaction_date.toString();
            //	System.out.println("--str---"+str);
        		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        		Date date = formatter.parse(str);
        		newDateString = new java.text.SimpleDateFormat(EXPECTED_DATE_FORMAT).format(date);
        		//response = "Your Airasia Savers Account Last Transaction Date is "+newDateString;
            	//System.out.println("Your Airasia Account Last Transaction Date is "+newDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
	        
	       
	        if(id ==7){
	        	response = "Your Airasia Savers Account Balance is "+Double.toString(balance);
	        }
	    	
	        if(id ==8){
	        //	response = "Your Airasia Savers Account Last Transaction is "+last_transaction;
	        	response = "Your Airasia Savers Account Last Transaction is "+last_transaction+" on "+newDateString+" with the purchase amount of "+last_transaction_amount;

	        }
	    	
	        if(id ==10){
	        	response ="Your Airasia Savers Account Last Transaction amount is "+Double.toString(last_transaction_amount);
	        }
	    	
	        if(id ==9){
	       
	        /*	final String EXPECTED_DATE_FORMAT = "yyyy-MM-dd";
	    		
	            try {
	            //	String str = "2016-06-15 00:00:00.0";
	            	String str = last_transaction_date.toString();
	            	System.out.println("--str---"+str);
	        		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	        		Date date = formatter.parse(str);
	        		String newDateString = new java.text.SimpleDateFormat(EXPECTED_DATE_FORMAT).format(date);
	        		response = "Your Airasia Savers Account Last Transaction Date is "+newDateString;
	            	System.out.println("Your Airasia Account Last Transaction Date is "+newDateString);
	            } catch (ParseException e) {
	                e.printStackTrace();
	            } */
        		response = "Your Airasia Savers Account Last Transaction Date is "+newDateString;

	        	
	        }
	        
	        if(id ==11){
	        	response ="Your Airasia Savers Account Number is "+accountnum;
	        }
	        
	        if(id ==12){
	        	
	        	String recent_transactions="";
		    	recent_transactions+="S.No		"+"Transaction		"+"Amount		"+"Date"+"\n";
		    	for(int i=0 ; i < airasiaList.size();i++){
		    		System.out.println("--Transaction---"+airasiaList.get(i).getTxdetail()+"--Amount Spent---"+airasiaList.get(i).getTxamount()+"---Date---"+airasiaList.get(i).getTxdate());
		   // 		recent_transactions +="--Transaction---"+airasiaList.get(i).getTxdetail()+"--Amount Spent---"+airasiaList.get(i).getTxamount()+"---Date---"+airasiaList.get(i).getTxdate()+"\n";
		    		recent_transactions +=i+1 +"			"+airasiaList.get(i).getTxdetail()+"			"+airasiaList.get(i).getTxamount()+"			"+ getTransactionDate(airasiaList.get(i).getTxdate())+"\n";

		    	}
		    	response ="Your Airasia Savers Account recent Transactions are below: "+"\n"+recent_transactions;
	        	
	        }
		}
        
		if(id >=13 && id <=19){
			Criteria criteria = getSession().createCriteria(MasterCard.class);
	    	criteria.add(Restrictions.eq("custid",customer.getId()));
	    	//Mastercard mastercard =(Mastercard)criteria.uniqueResult();
	    	
	    	
	      	criteria.addOrder(Order.desc("txdate"));
	    	List<MasterCard> masterList =criteria.list();
	    	MasterCard mastercard =(MasterCard)masterList.get(0);
	    	
	    	
	    	System.out.println("----mastercard Object----"+mastercard);
	    	double balance  = mastercard.getBalance();
	    	String  last_transaction  = mastercard.getTxdetail();
	    	double last_transaction_amount  = mastercard.getTxamount();
	    	Date last_transaction_date  = mastercard.getTxdate();
	    	int accountnum  = mastercard.getAccountnumber();
	    	Date expiry_date  = mastercard.getExpirydate();
	    	
	    	String newDateString = null;
	    	final String EXPECTED_DATE_FORMAT = "yyyy-MM-dd";
    	
            try {
            //	String str = "2016-06-15 00:00:00.0";
            	String str = last_transaction_date.toString();
            	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        		Date date = formatter.parse(str);
        		newDateString = new java.text.SimpleDateFormat(EXPECTED_DATE_FORMAT).format(date);
        	//	response = "Your CIMB Cash Rebate Platinum MasterCard last transaction date is "+newDateString;
            //	System.out.println("Your CIMB Cash Rebate Platinum MasterCard last transaction date  is "+newDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            
	        
	       
	        if(id ==13){
	        	response = "Your CIMB Cash Rebate Platinum MasterCard balance is "+Double.toString(balance);
	        }
	    	
	        if(id ==14){
	        	//response = "Your CIMB Cash Rebate Platinum MasterCard Last Transaction is "+last_transaction;
	        	response = "Your CIMB Cash Rebate Platinum MasterCard Last Transaction is "+last_transaction+" on "+newDateString+" with the purchase amount of "+last_transaction_amount;

	        }
	    	
	        if(id ==16){
	        	response ="Your CIMB Cash Rebate Platinum MasterCard Last Transaction amount is "+Double.toString(last_transaction_amount);
	        }
	    	
	        if(id ==15){
	       
	        /*	final String EXPECTED_DATE_FORMAT = "yyyy-MM-dd";
	    		
	            try {
	            //	String str = "2016-06-15 00:00:00.0";
	            	String str = last_transaction_date.toString();
	            	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	        		Date date = formatter.parse(str);
	        		String newDateString = new java.text.SimpleDateFormat(EXPECTED_DATE_FORMAT).format(date);
	        		response = "Your CIMB Cash Rebate Platinum MasterCard last transaction date is "+newDateString;
	            	System.out.println("Your CIMB Cash Rebate Platinum MasterCard last transaction date  is "+newDateString);
	            } catch (ParseException e) {
	                e.printStackTrace();
	            }*/
	            
        		response = "Your CIMB Cash Rebate Platinum MasterCard last transaction date is "+newDateString;

	        	
	        }
	        
	        if(id ==17){
	        	response ="Your CIMB Cash Rebate Platinum MasterCard Account Number is "+accountnum;
	        }
	        
	        if(id ==18){
	        	final String EXP_EXPECTED_DATE_FORMAT = "yyyy-MM-dd";
	    		
	            try {
	               	String str = expiry_date.toString();
	            	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	        		Date date = formatter.parse(str);
	        		String newExpDateString = new java.text.SimpleDateFormat(EXP_EXPECTED_DATE_FORMAT).format(date);
	        		response = "Your CIMB Cash Rebate Platinum MasterCard expiry date is "+newExpDateString;
	            	System.out.println("Your CIMB Cash Rebate Platinum MasterCard expiry date is "+newExpDateString);
	            } catch (ParseException e) {
	                e.printStackTrace();
	            } 
	            

	        }
	        
	        if(id ==19){
	        	String recent_transactions="";
		    	recent_transactions+="S.No		"+"Transaction		"+"Amount		"+"Date"+"\n";
		    	for(int i=0 ; i < masterList.size();i++){
		    		System.out.println("--Transaction---"+masterList.get(i).getTxdetail()+"--Amount Spent---"+masterList.get(i).getTxamount()+"---Date---"+masterList.get(i).getTxdate());
		    	//	recent_transactions +="--Transaction---"+masterList.get(i).getTxdetail()+"--Amount Spent---"+masterList.get(i).getTxamount()+"---Date---"+masterList.get(i).getTxdate()+"\n";
		    		recent_transactions +=i+1 +"			"+masterList.get(i).getTxdetail()+"			"+masterList.get(i).getTxamount()+"			"+ getTransactionDate(masterList.get(i).getTxdate())+"\n";
		    	}
		    	response ="Your CIMB Cash Rebate Platinum MasterCard recent Transactions are below: "+"\n"+recent_transactions;

	        }
	        
	       
	        
		}
		 if(id ==28){
	        	response=" You have the following accounts.Please let me know which account balance you need. "+"\n";
	     }
		 if(id ==29){
	        	response=" You have the following accounts.Please let me know which account recent transactions you need. "+"\n";
	     }  
		 if(id ==30){
	        	response=" You have the following accounts.Please let me know which account last transaction you need. "+"\n";
	     } 
		 if(id ==31){
	        	response=" You have the following accounts.Please let me know which account number you need. "+"\n";
	     }
		 if(id ==32 || id ==33 || id ==34){
	        	response=" Please let me know what information would you like to check. "+"\n";
	     }
		 if(id ==36){
	        	response=" I think you woould like to transfer the funds.Please fill the below details to proceed. "+"\n";
	     }
    	return response;
	}
    

	 public String getKeyValue(String key){
		    Map<String,String> intentMap = new HashMap<String,String>();
		    intentMap.put("501","Sorry, this bot deals with CIMB products Only");
		    intentMap.put("502","I am sorry, this bot is able to help you with CIMB products only");
		    intentMap.put("503","Sorry, I can only assist you with CIMB products");
		    intentMap.put("504","Sorry, please ask me regarding CIMB products only");
		    intentMap.put("505","I am sorry for not able to help you with this question, but I will be happy to assist you with any CIMB products inquiries or questions");
			return intentMap.get(key);
	}
	 
	 private int getRandomNumberInRange(int min, int max) {

			if (min >= max) {
				throw new IllegalArgumentException("max must be greater than min");
			}

			Random r = new Random();
			return r.nextInt((max - min) + 1) + min;
		}

   
	 public  String getTransactionDate(Date last_transaction_date) {
		 
			final String EXPECTED_DATE_FORMAT = "yyyy-MM-dd";
	    	String newDateString = null;
            try {
            	String str = last_transaction_date.toString();
        		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        		Date date = formatter.parse(str);
        		newDateString = new java.text.SimpleDateFormat(EXPECTED_DATE_FORMAT).format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
		 return newDateString;
	 }

    public void save(Customer cust) {
    		//Session session = this.sessionFactory.openSession();
    		//Transaction tx = getSession().beginTransaction();
    		//getSession().persist(cust);
    		getSession().save(cust);
    	//	tx.commit();
    		getSession().close();
    		
    	//getSession().persist(cust);
    	}


    	public List<Customer> list() {
    		/*Session session = this.sessionFactory.openSession();
    		List<Customer> custList = session.createQuery("from com.cimb.chatbot.model.Customer").list();
    		session.close();
    		return custList;
    		*/
    		Criteria criteria = getSession().createCriteria(Customer.class);
    		return (List<Customer>) criteria.list();
    	}
            
        
    	public void update(Customer cust) {
    	/*	Session session = this.sessionFactory.openSession();
                    Transaction tx = session.beginTransaction();
                    Query query =session.createQuery("update com.cimb.chatbot.model.Customer set name=:n , balance=:bal where id=:i");
                    query.setParameter("n", cust.getName());
                    query.setParameter("bal",cust.getBalance());
                    query.setParameter("i",cust.getId());
                    int status =query.executeUpdate();
                    System.out.println("-------status---"+status);
                    tx.commit();
    		session.close();
    		*/
    		getSession().update(cust);
    	}

         
    	public void delete(Customer cust) {
    	/*	Session session = this.sessionFactory.openSession();
                    Transaction tx = session.beginTransaction();
                    Query query =session.createQuery("delete from com.cimb.chatbot.model.Customer where id=:i");
                    query.setParameter("i",cust.getId());
                    int status =query.executeUpdate();
                    System.out.println("-------status---"+status);
                    tx.commit();
    		session.close();*/
    		Query query = getSession().createSQLQuery("delete from com.cimb.chatbot.model.Customer where id=:i");
    		query.setParameter("i",cust.getId());
    		query.executeUpdate();
    	}


		public int getAccount(String type, int custid) {
			int accountnum =0;
				if(type.equalsIgnoreCase("kwik")) {
				    Criteria criteria = getSession().createCriteria(Kwik.class);
			    	criteria.add(Restrictions.eq("custid",custid));
			      	criteria.addOrder(Order.desc("txdate"));
			    	List<Kwik> kwikList =criteria.list();
			    	Kwik kwik =(Kwik)kwikList.get(0);
			    	accountnum  = kwik.getAccountnumber();
				}
				if(type.equalsIgnoreCase("airasia")) {
				    Criteria criteria = getSession().createCriteria(Airasia.class);
			    	criteria.add(Restrictions.eq("custid",custid));
			      	criteria.addOrder(Order.desc("txdate"));
			    	List<Airasia> airasiaList =criteria.list();
			    	Airasia airasia =(Airasia)airasiaList.get(0);
			    	accountnum  = airasia.getAccountnumber();
				}
				if(type.equalsIgnoreCase("mastercard")) {
				    Criteria criteria = getSession().createCriteria(Airasia.class);
			    	criteria.add(Restrictions.eq("custid",custid));
			      	criteria.addOrder(Order.desc("txdate"));
			    	List<MasterCard> mastercardList =criteria.list();
			    	MasterCard airasia =(MasterCard)mastercardList.get(0);
			    	accountnum  = airasia.getAccountnumber();
				}

			
			
			return accountnum;
		}

		public double getBalance(String type, int custid) {
			double balance =0.0;
			if(type.equalsIgnoreCase("kwik")) {
			    Criteria criteria = getSession().createCriteria(Kwik.class);
		    	criteria.add(Restrictions.eq("custid",custid));
		      	criteria.addOrder(Order.desc("txdate"));
		    	List<Kwik> kwikList =criteria.list();
		    	Kwik kwik =(Kwik)kwikList.get(0);
		    	balance  = kwik.getBalance();
			}
			if(type.equalsIgnoreCase("airasia")) {
			    Criteria criteria = getSession().createCriteria(Airasia.class);
		    	criteria.add(Restrictions.eq("custid",custid));
		      	criteria.addOrder(Order.desc("txdate"));
		    	List<Airasia> airasiaList =criteria.list();
		    	Airasia airasia =(Airasia)airasiaList.get(0);
		    	balance  = airasia.getBalance();
			}
			if(type.equalsIgnoreCase("mastercard")) {
			    Criteria criteria = getSession().createCriteria(Airasia.class);
		    	criteria.add(Restrictions.eq("custid",custid));
		      	criteria.addOrder(Order.desc("txdate"));
		    	List<MasterCard> mastercardList =criteria.list();
		    	MasterCard airasia =(MasterCard)mastercardList.get(0);
		    	balance  = airasia.getBalance();
			}

		
		
		return balance;
		}


		public boolean transferAmount(com.cimb.chatbot.model.Transaction transaction) {
			
			double amount = transaction.getAmount();
			int fromAcc = transaction.getFrom();
			int toAcc = transaction.getTo();
			String fromtype = null, totype = null;
			int  custid = transaction.getCustid();
			boolean flag = false;
			Transaction tx =getSession().beginTransaction();
			String queryStr = null;
			
			if( fromAcc == 11111111 || toAcc == 11111111){
				fromtype = "Kwik";
				totype = "Kwik";
				queryStr = "update com.cimb.chatbot.model.Kwik  set balance=:newbal,tx_date=:txdate where custid=:id";
			}
			if( fromAcc == 10000000 || toAcc == 10000000){
				fromtype = "airasia";
				totype = "airasia";
				queryStr = "update com.cimb.chatbot.model.Airasia  set balance=:newbal,tx_date=:txdate where custid=:id";
			}
			if( fromAcc == 11000000 || toAcc == 11000000){
				fromtype = "mastercard";
				totype = "mastercard";
				queryStr = "update com.cimb.chatbot.model.MasterCard  set balance=:newbal,tx_date=:txdate  where custid=:id";
			}
			
			double balance =getBalance(fromtype,custid);
			if( amount < balance) {
				double newbalance =  balance - amount;
				
				Query query = getSession().createQuery(queryStr);
				query.setParameter("newbal", newbalance);
				query.setParameter("id", custid);
				query.setParameter("txdate", transaction.getTxdate());
				query.executeUpdate();
				
				
			/*	double balance1 =getBalance(totype,custid);
				double newbalance1 =  balance1 + amount;
				
				Query query1 = getSession().createQuery(queryStr);
				query1.setParameter("newbal", newbalance1);
				query1.setParameter("id", custid);
				query.setParameter("txdate", transaction.getTxdate());
				query1.executeUpdate();*/
				
				com.cimb.chatbot.model.Transaction transact = new com.cimb.chatbot.model.Transaction();
				transact.setCustid(custid);
				transact.setFrom(fromAcc);
				transact.setTo(toAcc);
				transact.setAmount(amount);
				transact.setRemarks(transaction.getRemarks());
				transact.setTxdate(transaction.getTxdate());
				transact.setCode("200");
				
				getSession().save(transact);
		    	
				flag = true;
			} else{
				flag = false;
			}
			
			tx.commit();
			getSession().flush();
			getSession().close();
			
			return flag;
		}
		
		
public List<Account> getAccounts(int custid) {
			
			List <Account> accList = new ArrayList<Account>();
			Account []accData = new Account[3];
			
			double balance  = 0.0;
			int accnum =0;
			
			Criteria criteria = getSession().createCriteria(Kwik.class);
		    criteria.add(Restrictions.eq("custid",custid));
		    criteria.addOrder(Order.desc("txdate"));
		    List<Kwik> kwikList =criteria.list();
		    Kwik kwik =(Kwik)kwikList.get(0);
		    balance  = kwik.getBalance();
		    accnum = kwik.getAccountnumber();
		//	balance = 100.00;
		//	accnum = 11111111;
			
			accData[0] = new Account();
		    accData[0].setId(1);
            accData[0].setType("Kwik Account");
            accData[0].setAccountnumber(accnum);
            accData[0].setBalance(balance);
            accList.add(accData[0]);
		    
		    
            Criteria criteria1 = getSession().createCriteria(Airasia.class);
	    	criteria1.add(Restrictions.eq("custid",custid));
	      	criteria1.addOrder(Order.desc("txdate"));
	    	List<Airasia> airasiaList =criteria1.list();
	    	Airasia airasia =(Airasia)airasiaList.get(0);
	    	balance  = airasia.getBalance();
	    	accnum = airasia.getAccountnumber();
	    	
         //   balance = 500.00;
		//	accnum = 10000000;
			
			accData[1] = new Account();
	    	accData[1].setId(2);
            accData[1].setType("AirAsia Savers Account");
            accData[1].setAccountnumber(accnum);
            accData[1].setBalance(balance);
            accList.add(accData[1]);	
			
            Criteria criteria2 = getSession().createCriteria(MasterCard.class);
	    	criteria2.add(Restrictions.eq("custid",custid));
	      	criteria2.addOrder(Order.desc("txdate"));
	    	List<MasterCard> mastercardList =criteria2.list();
	    	MasterCard mastercard =(MasterCard)mastercardList.get(0);
	    	balance  = mastercard.getBalance();
	    	accnum = mastercard.getAccountnumber();
            
         //   balance = 500.00;
		//	accnum = 11000000;
	    	
			accData[2] = new Account();
	    	accData[2].setId(3);
            accData[2].setType("Cash Rebate Platinum Master Card Account");
            accData[2].setAccountnumber(accnum);
            accData[2].setBalance(balance);
            accList.add(accData[2]);

			
			return accList;
		}


}
