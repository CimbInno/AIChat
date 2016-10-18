package com.cimb.chatbot.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transaction")
public class Transaction {
    
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)    
	private int id;
	
	@Column(name="custid")
	private int custid;
	
	@Column(name="fromacc")
	private int from;
	
	@Column(name="toacc")
	private int to;
	
	private double amount;  
	
	@Column(name="tx_date")
	private Date txdate;
	
	@Column(name="remarks")
    private String remarks; 
	
	
	private String code; 
	
	    
	public Transaction() {
	
	}

    public Transaction(int id, int custid,int from,int to,double amount,Date txdate,String remarks,String code) {
        this.id = id;
        this.custid = custid;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.txdate =txdate;
        this.remarks = remarks;
        this.code = code;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getTxdate() {
		return txdate;
	}

	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Override
	public String toString(){
		return "id="+id+", custid="+custid+", from="+from+",to="+to+",amount="+amount+",txdate="+txdate+",remarks="+remarks+",code="+code;
	}
}