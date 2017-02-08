package com.pat.nbp.explore.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pat.nbp.explore.dao.CurrenciesDAO;
import com.pat.nbp.explore.dao.Currency;
import com.pat.nbp.explore.dao.CurrenciesDAO.NbpTableType;

/**
 * An application bean provides available currencies list. List is creating on
 * bean startup.
 * 
 * @author Andrzej Poloczek
 * @version 1.0
 */
@Component
@Scope(value = "application")
public class Currencies {

	/**
	 * List of available currrencies.
	 */
	private List<Currency> currencies = new ArrayList<>();
	
	/**
	 * Retrieving available currencies on startup.
	 */
	@Autowired
	public void retriveCurriencieFullsList() {
		CurrenciesDAO dao = new CurrenciesDAO();
		currencies = dao.parse(NbpTableType.A);
	}
	
	/**
	 * Getting a list of available curriences.
	 * @return
	 */
	public List<Currency> getCurrenciessFull() {
		return currencies;
	}
	
	/**
	 * Getting a map of available currencies. This is prepared to
	 * create a drop-down list with flagstrap component.  
	 * @return
	 */
	public Map<String, String> getCurrenciessFullAsChooser() {
		Map<String, String> map = new TreeMap<>();
		
		for (Currency c : currencies) {
			map.put(c.getCode(), c.getCode()+": "+c.getName());
		}
		
		return map;
	}
	
}
