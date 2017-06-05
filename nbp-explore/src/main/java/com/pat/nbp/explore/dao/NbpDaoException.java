package com.pat.nbp.explore.dao;

/**
 * An exception class for DAO objects.
 * 
 * @author Andrzej Poloczek
 * @version 1.0
 */
public class NbpDaoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NbpDaoException(String message) {
		super(message);
	}
}
