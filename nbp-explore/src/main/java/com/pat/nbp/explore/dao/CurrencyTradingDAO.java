package com.pat.nbp.explore.dao;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/**
 * A data access object class to retrieve data from <b>api.nbp.pl</b>.
 * 
 * @author Andrzej Poloczek
 * @version 1.0
 */
public class CurrencyTradingDAO {

	private final String currency;
	private final LocalDate from;
	private final LocalDate to;
	private final String url = "http://api.nbp.pl/api/exchangerates/rates/a/_CURRENCY_/_FROM_/_TO_/?format=json";
	private final CurrencyTrading trading = new CurrencyTrading();
	
	/**
	 * Construct a DAO object. If find any incorrect in dates throws an
	 * exception. 
	 * 
	 * @param currency A currency code to get.
	 * @param from A from date
	 * @param to A to date
	 * @throws NbpDaoException
	 */
	public CurrencyTradingDAO(String currency, LocalDate from, LocalDate to) throws NbpDaoException {
		
		if (currency == null || currency.isEmpty()) {
			throw new NbpDaoException("Currency code can not be empty.");
		}
		
		if (from == null || to == null) {
			throw new NbpDaoException("Dates can not be empty.");
		}
		
		if (!from.isBefore(to)) {
			throw new NbpDaoException(" The 'From' date must be before the 'to' date.");
		}
		
		this.currency = currency;
		this.from = from;
		this.to= to;
	}
	
	
	/**
	 * Getting a results as a <i>CurrencyTrading</i> class object.
	 * @return
	 */
	public CurrencyTrading getCurrencyTrading() {
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
		
		// prepare an url
		String _url = url.replaceAll("_CURRENCY_", currency)
						 .replaceAll("_FROM_", from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
						 .replaceAll("_TO_", to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		
		JsonFactory jf = new JsonFactory();
		// get data stream from url
		try {
			System.out.println("Retrieve an url: " + _url);
			JsonParser jp = jf.createJsonParser(new URL(_url));
			
			// A currency result object 
			Currency curr = new Currency();
			// Ratings list object
			List<Rate> rl = new ArrayList<>();
			
			while (jp.nextToken() != JsonToken.END_OBJECT) {
				
				// As first parsed are a currency information
				String fn = jp.getCurrentName();
				if ("currency".equals(fn)) {
					jp.nextToken();
					curr.setName(jp.getText());
				} else if ("code".equals(fn)) {
					jp.nextToken();
					curr.setCode(jp.getText());
				} if ("rates".equals(fn)) {
					// Starts a rates list
					jp.nextToken();
					// Current rating object
					Rate rate = new Rate();
					// iterate
					while (jp.nextToken() != JsonToken.END_ARRAY) {
						// actual rate field name
						fn = jp.getCurrentName();
						
						if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
							// start rate object
							rate = new Rate();
						} else if (jp.getCurrentToken() == JsonToken.END_OBJECT) {
							// end rate
							rl.add(rate);
						} else {
							// adding currency data
							if ("effectiveDate".equals(fn)) {
								jp.nextToken();
								rate.setDate(toLocalDate(jp.getText()));
							} else if ("mid".equals(fn)) {
								jp.nextToken();
								rate.setBid(Double.parseDouble(jp.getText()));
							}
						}
					}
				}
			}
			trading.setCurrency(curr);
			trading.setRates(rl);
			System.out.println(trading);
		} catch (IOException e) {
			e.printStackTrace();
			throw new NbpDaoException("Found an error while parsing data: " + e.getMessage());
		}
	}
}
