package com.cimb.chatbot.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class Customer {
    
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)    
	private int id;
	
	private String name;  
	
	private String query;  
	
	private String response;
	
	private double balance;  
	
	private String code; 
	    
	public Customer() {
	
	}

    public Customer(int id, String name, String query,double balance,String response,String code) {
        this.id = id;
        this.name = name;
        this.query = query;
        this.balance = balance;
        this.response = response;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

    public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString(){
		return "id="+id+", name="+name+", query="+query+",balance="+balance+",response="+response+",code="+code;
	}
}  