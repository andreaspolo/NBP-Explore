package com.pat.nbp.explore.dao;

import java.time.LocalDate;

/**
 * A single rate info. Class stores information about bid and date of current rate.
 * 
 * @author Andrzej Poloczek
 * @version 1.0
 */
public class Rate {

	/**
	 * A date if current rate
	 */
	private LocalDate date;
	
	/**
	 * A bid of current rate
	 */
	private double bid;
	
	
	public Rate() {
		
	}
	
	public Rate(LocalDate date, double bid) {
		this.date = date;
		this.bid = bid;
	}
	
	/**
	 * Get a date of current rate.
	 * @return
	 */
	public LocalDate getDate() {
		return date;
	}
	
	/**
	 * Set a date of current rate.
	 * @param date Rating date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	/**
	 * Get a bid of current rate.
	 * @return
	 */
	public double getBid() {
		return bid;
	}
	
	/**
	 * Set a bid of current rate.
	 * @param bid Bid of current rate
	 */
	public void setBid(double bid) {
		this.bid = bid;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + "[date = " + date + ", bid = " + bid + "]";
	}
}
