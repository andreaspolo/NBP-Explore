package com.pat.nbp.explore.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * A DTO object class to transfer a home form values.
 * 
 * @author Andrzej Poloczek
 * @version 1.0
 */
public class HomeForm {

	/**
	 * Date in specified format. Date can be null.
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate listingDate;

	public HomeForm(LocalDate listingDate) {
		this.listingDate = listingDate;
	}

	public HomeForm() {

	}
	
	/**
	 * Getting a listing date.
	 * @return
	 */
	public LocalDate getListingDate() {
		return listingDate;
	}

	/**
	 * Setting a listing date
	 * @param listingDate New listing date
	 */
	public void setListingDate(LocalDate listingDate) {
		this.listingDate = listingDate;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + "[listingDate = " + listingDate + "]";
	}
}
