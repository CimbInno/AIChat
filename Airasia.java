package com.cimb.chatbot.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="airasia")
public class Airasia {
    
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)    
	private int id;
	
	@Column(name="custid")
	private int custid;
	
	@Column(name="accountno")
	private int accountnumber;
	
	@Column(name="balance")
	private double balance;
	
	@Column(name="tx_date")
	private Date txdate;
	
	@Column(name="tx_detail")
    private String txdetail;
	
	@Column(name="tx_amount")
    private double txamount;
	
	public Airasia() {
		
	}
	
	 public Airasia(int id, int custid,int accountnumber,double balance,Date txdate,String txdetail,double txamount) {
	        this.id = id;
	        this.custid = custid;
	        this.accountnumber =accountnumber;
	        this.balance = balance;
	        this.txdate =txdate;
	        this.txdetail =txdetail;
	        this.txamount = txamount;
	       
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
	public int getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(int accountnumber) {
		this.accountnumber = accountnumber;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public Date getTxdate() {
		return txdate;
	}
	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}
	public String getTxdetail() {
		return txdetail;
	}
	public void setTxdetail(String txdetail) {
		this.txdetail = txdetail;
	}
	public double getTxamount() {
		return txamount;
	}
	public void setTxamount(double txamount) {
		this.txamount = txamount;
	}
	
	 @Override
		public String toString(){
			return "id="+id+", custid="+custid+", accountnumber="+accountnumber+",balance="+balance+",txdate="+txdate+",txdetail="+txdetail+",txamount="+txamount;
		}
	
}
