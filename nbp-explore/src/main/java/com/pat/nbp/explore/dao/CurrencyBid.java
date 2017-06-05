package com.pat.nbp.explore.dao;

/**
 * Class represents a single bid for a currency
 * 
 * @author Andrzej Poloczek
 * @version 1.0
 */
public class CurrencyBid {
	
	private Currency currency;
	private double bid;

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public double getBid() {
		return bid;
	}

	public void setBid(double bid) {
		this.bid = bid;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + "[currency = " + currency + ", bid = " + bid + "]";
	}
}
