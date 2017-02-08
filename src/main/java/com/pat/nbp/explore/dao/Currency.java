package com.pat.nbp.explore.dao;

/**
 * A class represents a single currency object.  
 * 
 * @author Andrzej Polczek
 * @version 1.0
 */
public class Currency {

	private String code;
	private String name;
	
	public Currency() {
		
	}
	
	/**
	 * Get currency code.
	 * 
	 * @param code
	 * @param name
	 */
	public Currency(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	/**
	 * Set currency code.
	 * @return
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Get currency name.
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * Set currency name.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[code = " + code + ", name = " + name + "]";
	}
}
