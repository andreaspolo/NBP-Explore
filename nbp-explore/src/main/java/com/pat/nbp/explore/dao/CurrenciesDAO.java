package com.pat.nbp.explore.dao;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/**
 * Class used to parse JSON results from <i>api.nbp.pl</i> into currencies list.<br><br>
 * It is mostly use in application context component to retrieve available currencies and
 * store in application context. 
 * 
 * @author Andrzej Poloczek
 * @version 1.0
 */
public class CurrenciesDAO {

	/**
	 * Available tables type. An api from <b><i>api.nbp.pl</i></b> share a 3 types of
	 * results tables. Each one contains different data.
	 *  
	 * @author Andrzej Poloczek
	 * @version 1.0
	 */
	public enum NbpTableType {
		A,
		B,
		C
	};
	
	/**
	 * An URL to get json data.
	 */
	private final String url = "http://api.nbp.pl/api/exchangerates/tables/__TABLE__/?format=json";
	
	/**
	 * Parsing a json results into list of currencies.<br><br>
	 * An api from <b><i>api.nbp.pl</i></b> share a 3 types of results tables. Each one 
	 * contains different data. For this method differences in structure have no
	 * meaning at all, because it reads only data about currency, and this data are
	 * same fo each type.<br>
	 * A difference is only in amount of currencies id results, each table type contains
	 * different currencies. 
	 * 
	 * @param type Table type from <b><i>api.nbp.pl</i></b>
	 * @return List of currencies
	 */
	public List<Currency> parse(NbpTableType type) {
		
		System.out.println("CurrenciesParser:oarse() - Start");
		
		// create a result object
		List<Currency> currencies = new ArrayList<>();
		// create a factory
		JsonFactory jf = new JsonFactory();
		
		try {
			// get data stream from url
			JsonParser jp = jf.createJsonParser(new URL(url.replaceAll("__TABLE__", type.toString())));
			
			while (jp.nextToken() != JsonToken.END_OBJECT) {
				// actual fieldname
				String fieldname = jp.getCurrentName();
				
				if ("table".equals(fieldname)) {
					jp.nextToken();
					System.out.println("TABLE TYPE IS: "+jp.getText());
				} else if ("effectiveDate".equals(fieldname)) {
					jp.nextToken();
					System.out.println("DATE: "+jp.getText());
				} if ("rates".equals(fieldname)) {
					// Start iteration by rates
					jp.nextToken();
					// Prepare currency object
					Currency cur = new Currency();
					// iterate
					while (jp.nextToken() != JsonToken.END_ARRAY) {
						// actual rate fieldname
						fieldname = jp.getCurrentName();
						
						if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
							// start rate object starts a new currency object
							cur = new Currency();
						} else if (jp.getCurrentToken() == JsonToken.END_OBJECT) {
							// end rate object end a currency object - save it to results
							currencies.add(cur);
						} else {
							// adding currency data
							if ("currency".equals(fieldname)) {
								jp.nextToken();
								cur.setName(jp.getText());
							} else if ("code".equals(fieldname)) {
								jp.nextToken();
								cur.setCode(jp.getText());
							}
						}
					}
				}
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return currencies;
	}
}
