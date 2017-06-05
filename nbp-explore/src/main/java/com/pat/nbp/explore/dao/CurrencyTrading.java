package com.pat.nbp.explore.dao;

import java.util.List;

/**
 * A trading for a single currency. In this class are stored a currency informationa ans
 * rates for specified time interval.<br>
 * Objects of this class act as a data transfer objects. 
 * 
 * @author Andrzej Poloczek
 * @version 1.0
 */
public class CurrencyTrading {

	/**
	 * Currency information
	 */
	private Currency currency;
	
	/**
	 * Rates for a currency
	 */
	private List<Rate> rates;
	
	
	/**
	 * An empty constructor.
	 */
	public CurrencyTrading() {
		
	}
	
	/**
	 * A constructor.
	 * 
	 * @param currency
	 * @param ratings
	 */
	public CurrencyTrading(Currency currency,List<Rate> rates) {
		this.currency = currency;
		this.rates = rates;
	}
	
	
	/**
	 * Get a currency information.
	 * @return
	 */
	public Currency getCurrency() {
		return currency;
	}
	
	/**
	 * Set a currency information
	 * @param currency A currency information
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * Get a rates list for current currency.
	 * @return
	 */
	public List<Rate> getRates() {
		return rates;
	}

	/**
	 * Set a rates list for a current currency.
	 * @param rates Ratings list.
	 */
	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[currency = " + currency + ", rates = " + rates + "]";
	}
}
