package com.pat.nbp.explore.dao;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/**
 * A data access object class to retrieve data from <b>api.nbp.pl</b>.
 * 
 * @author Andrzej Poloczek
 * @version 1.0
 */
public class CurrenciesTradingDAO {

	private final LocalDate date;
	private final String url = "http://api.nbp.pl/api/exchangerates/tables/A/_DATE_?format=json";
	private final CurrenciesTrading trading = new CurrenciesTrading();
	
	/**
	 * Constructor accepts a listing date parameter. <b>It's available to set date as
	 * null</b>, then class search last listing. 
	 * 
	 * @param date Listing date. If null then search for last listing.
	 * @throws NbpDaoException 
	 */
	public CurrenciesTradingDAO(LocalDate date) {
		this.date = date;
	}
	
	/**
	 * Getting a results object. Results represents <b>CurrenciesTrading</b>
	 * object.
	 * 
	 * @return Results
	 * @see com.pat.nbp.explore.dao.CurrenciesTrading
	 */
	public CurrenciesTrading getCurrenciesTrading() {
		return trading;
	}
	
	
	/**
	 * Parsing result date to LocalDate object.
	 * @param date
	 * @return
	 */
	private LocalDate toLocalDate(String date) {
		
		String[] d = date.split("-");
		return LocalDate.of(
				Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2])
		);
		
	}
	
	/**
	 * Parsing a json data downloaded from <i>api.nbe.pl</i> service.
	 * @throws NbpDaoException
	 */
	public void parse() throws NbpDaoException {
		
		// prepare date - date can be null
		String _date = (date != null) ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
		
		// prepare an url
		String _url = url.replaceAll("_DATE_", _date);
		
		JsonFactory jf = new JsonFactory();
		// get data stream from url

		System.out.println("Retrieve an url: " + _url);
		@SuppressWarnings("deprecation")
		JsonParser jp;
		try {
			jp = jf.createJsonParser(new URL(_url));
			
			while (jp.nextToken() != JsonToken.END_OBJECT) {
				
				// As first parsed are a currency information
				String fn = jp.getCurrentName();
				if ("no".equals(fn)) {
					jp.nextToken();
					trading.setNumber(jp.getText());
				} else if ("effectiveDate".equals(fn)) {
					jp.nextToken();
					trading.setDate(toLocalDate(jp.getText()));
				} if ("rates".equals(fn)) {
					// Starts a rates list
					jp.nextToken();
					
					trading.setBids(new ArrayList<>());
					
					CurrencyBid cb = new CurrencyBid();
					cb.setCurrency(new Currency());
					
					// iterate
					while (jp.nextToken() != JsonToken.END_ARRAY) {
						// actual rate field name
						fn = jp.getCurrentName();
						
						if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
							// start rate object
							cb = new CurrencyBid();
							cb.setCurrency(new Currency());
						} else if (jp.getCurrentToken() == JsonToken.END_OBJECT) {
							// end rate
							trading.getBids().add(cb);
						} else {
							// adding currency data
							if ("currency".equals(fn)) {
								jp.nextToken();
								cb.getCurrency().setName(jp.getText());
							} else if ("code".equals(fn)) {
								jp.nextToken();
								cb.getCurrency().setCode(jp.getText());
							} else if ("mid".equals(fn)) {
								jp.nextToken();
								cb.setBid(Double.parseDouble(jp.getText()));
							}
						}
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new NbpDaoException("Found an error while parsing data: " + e.getMessage());
		}
		
		
		System.out.println(trading);
	}
	
}
