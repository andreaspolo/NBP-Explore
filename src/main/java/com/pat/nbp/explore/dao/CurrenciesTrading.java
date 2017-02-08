package com.pat.nbp.explore.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Andrzej Poloczek
 * @version 1.0
 */
public class CurrenciesTrading {

	/**
	 * A trading number.
	 */
	private String number;
	
	/**
	 * A trading date.
	 */
	private LocalDate date;
	
	/**
	 * List of currency bids.
	 */
	private List<CurrencyBid> bids; 

	public CurrenciesTrading() {

	}
	
	public CurrenciesTrading(String number, LocalDate date) {
		this.setNumber(number);
		this.setDate(date);
		this.setBids(new ArrayList<>());
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<CurrencyBid> getBids() {
		return bids;
	}

	public void setBids(List<CurrencyBid> bids) {
		this.bids = bids;
	}	
	
	@Override
	public String toString() {
		return this.getClass().getName() + "[number = " + number + ", date = " + date + ",bids = " + bids + "]";
	}
	
}
