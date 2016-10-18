package com.cimb.chatbot.service;

import java.util.List;

import com.cimb.chatbot.model.History;

public interface HistoryService {
	 public void save(History history);
	// public void update(History history);
	// public void delete(History history);
	 public History list(int id);
	 public void update(History history);
	 public int getPreviousIntentId();
	 public String getProductName();
}
