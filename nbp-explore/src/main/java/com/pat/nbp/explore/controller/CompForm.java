package com.pat.nbp.explore.controller;

import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;


/**
 * A DTO object class to transform comparision form values. It is used to compare a
 * currencies listings.
 * 
 * @author Andrzej Poloczek
 * @version 1.0
 */
public class CompForm {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate listingDateFrom;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate listingDateTo;
	
	/**
	 * A list of currency codes to compare
	 */
	private List<String> countries;
	
	public CompForm() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public LocalDate getListingDateFrom() {
		return listingDateFrom;
	}
	
	public void setListingDateFrom(LocalDate listingDateFrom) {
		this.listingDateFrom = listingDateFrom;
	}
	
	public LocalDate getListingDateTo() {
		return listingDateTo;
	}
	
	public void setListingDateTo(LocalDate listingDateTo) {
		this.listingDateTo = listingDateTo;
	}

	public List<String> getCountries() {
		return countries;
	}

	public void setCountries(List<String> countries) {
		this.countries = countries;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() 
				+ "[listingDateFrom = "+listingDateFrom+", "
				+ "listingDateTo = "+listingDateTo+", "
				+ "countrie = , "+countries+"]";
	}
}
