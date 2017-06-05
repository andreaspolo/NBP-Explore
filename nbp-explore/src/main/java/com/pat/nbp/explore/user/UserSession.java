package com.pat.nbp.explore.user;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * User session bean. It holds all current logged defaults or values.
 * 
 * @author Andrzej Poloczek
 * @version 1.1
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession {

	/**
	 * A currency
	 */
	private String currency;
	
	/**
	 * A list of currency codes to compare
	 */
	private List<String> curriences;
	
	/**
	 * A date
	 */
	private LocalDate date;
	
	/**
	 * A date from 
	 */
	private LocalDate dateFrom;
	
	/**
	 * A date to
	 */
	private LocalDate dateTo;

	
	/**
	 * Getting default currency code. If not set reurns empty string, <b>never null</b>. 
	 * @return Default currency code
	 */
	public String getCurrency() {
		return (currency != null)?currency:"";
	}

	/**
	 * Setting default currency code.
	 * @param defaultCurrency Default currency code.
	 */
	public void setCurrency(String defaultCurrency) {
		this.currency = defaultCurrency;
	}

	/**
	 * Getting a start date from session.
	 * @return
	 */
	public LocalDate getDateFrom() {
		return dateFrom;
	}

	/**
	 * Setting a start date in session.
	 * @param dateFrom
	 */
	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * Getting an end date from session.
	 * @return
	 */
	public LocalDate getDateTo() {
		return dateTo;
	}

	/**
	 * Setting an end date to session.
	 * @param dateTo
	 */
	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * Get a curriences list to compare
	 * @return
	 */
	public List<String> getCurriences() {
		return curriences;
	}

	/**
	 * Set a curriences list to compare
	 * @param countries
	 */
	public void setCurriences(List<String> curriences) {
		this.curriences = curriences;
	}

	/**
	 * Getiting a date from session.
	 * @return
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Setting a date into session.
	 * @param date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}
}
