package com.pat.nbp.explore.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.pat.nbp.explore.app.U;

/**
 * A trading data prepared to create a chart with <b>chaertjs</b> library. 
 * 
 * @author Andrzej Poloczek
 * @version 1.0
 */
public class TradingChart {

	/**
	 * A chart labels. In chartjs it is a list of dates, passed as a Strings. 
	 */
	private List<String> labels;
	
	/**
	 * A chart data. It contains a 2 values in map: <br>
	 * <ul>
	 * <li>currency - a String value with a currency code.</li>
	 * <li>rates - a List<Double> value contains a list of rates for current currency.
	 * </ul>
	 */
	private List<Map<String, Object>> data;
	
	public TradingChart(CurrencyTrading trading) {
		if (trading != null) {
			List<CurrencyTrading> list = new ArrayList<>();
			list.add(trading);
			build(list);
		}
	}
	
	
	/**
	 * A Constructor. It builds a chart data from tradings object, passed as a parameter.
	 * 
	 * @param trading A list of trading object to 
	 */
	public TradingChart(List<CurrencyTrading> trading) {
		if (trading != null) {
			build(trading);
		}
	}
	
	private void build(List<CurrencyTrading> trading) {
		this.labels = null;
		this.data = new ArrayList<>();
		
		for (CurrencyTrading ct: trading) {
			// create labels only one time, when it is null.
			if (labels == null) {
				labels = ct.getRates().stream().map(rating -> rating.getDate().format(U.f))
						.collect(Collectors.toList());
			}
			// prepare a data
			Map<String,Object> map = new HashMap<>();
			// a currency code to show
			map.put("currency", ct.getCurrency().getCode());
			// a rates for a currency
			map.put("rates",
					ct.getRates().stream().map(rating -> rating.getBid()).collect(Collectors.toList())
			);
			// add to data set
			data.add(map);
		}
	}

	/**
	 * Getting a chart labels. In chartjs it is a list of dates passed as a string values.
	 * @return Lisr of chart labels
	 */
	public List<String> getLabels() {
		return labels;
	}

	/**
	 * Getting a chart data sets. In chartjs each data set needs a currency code and a list 
	 * of rates for each currency, so this method returns a map with 2 value:<br>
	 * <ul>
	 * <li>currency - a String value with a currency code.</li>
	 * <li>rates - a List<Double> value contains a list of rates for current currency.
	 * </ul>
	 * @return A chart data sets
	 */
	public List<Map<String, Object>> getData() {
		return data;
	}
	
}
