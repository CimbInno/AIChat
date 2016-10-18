package com.cimb.chatbot.dao;

import java.util.List;

import com.cimb.chatbot.model.History;

public interface HistoryDAO {
	 public void save(History history);
	 public void update(History history);
	 public void delete(History history);
	 public History list(int id);
	 public int getPreviousIntentId();
	 public String getProductName();

}
