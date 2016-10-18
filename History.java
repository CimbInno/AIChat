package com.cimb.chatbot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="history")
public class History {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)    
	private int id;
	
	private int custid;
	
	@Column(name="intentId")
	private int intentid;
	
	private String vector;  
	
	private String query;  
	
	private String response;
	
	private String product;
	
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustid() {
		return custid;
	}

	public void setCustid(int custid) {
		this.custid = custid;
	}

	public String getVector() {
		return vector;
	}

	public void setVector(String vector) {
		this.vector = vector;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
	public int getIntentid() {
		return intentid;
	}

	public void setIntentid(int intentid) {
		this.intentid = intentid;
	}

	
	
	public History() {
		
	}

    public History(int id, int custid,int intentid,String vector, String query,String response,String product) {
        this.id = id;
        this.custid = custid;
        this.intentid = intentid;
        this.vector = vector;
        this.query = query;
        this.response = response;
        this.product = product;
    }
	
}
