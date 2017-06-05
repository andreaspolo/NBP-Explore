package com.pat.nbp.explore.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pat.nbp.explore.app.Currencies;
import com.pat.nbp.explore.dao.CurrencyTrading;
import com.pat.nbp.explore.dao.CurrencyTradingDAO;
import com.pat.nbp.explore.dao.NbpDaoException;
import com.pat.nbp.explore.dao.Rate;
import com.pat.nbp.explore.dao.TradingChart;
import com.pat.nbp.explore.user.UserSession;

/**
 * A controller for comparision funcionality. The comparision view expect a following
 * data:<br><br>
 * A table data with following format:<br>
 * List{ // A list of rows for each currency
 * 	Map { // A map of values for each row
 * 		currency: a Currency class object 
 * 		first: A double value with first found rate
 * 		min: A double value with minimal found rate
 * 		max: A double value with maximal found rate
 * 		last: A double value with last found rate
 * 	}
 * }
 * 
 * @author Andrzej Poloczek
 * @version 1.0
 */
@Controller
public class ComparisionController {

	@Autowired
	private Currencies currencies;
	
	@Autowired
	private UserSession userSession;
	
	
	@RequestMapping(value = "/comp", method = RequestMethod.GET)
	public String comp(@Valid CompForm compForm, BindingResult br, Model model) {
		
		prepareDefaults(compForm);
		process(compForm, model);
		
		return "comp";
	}
	
	@RequestMapping(value = "/comp", method = RequestMethod.POST)
	public String compPost(@Valid CompForm compForm, BindingResult br, Model model) {
		
		if (br.hasErrors()) {
			System.out.println("FOUND ERRORS: " + br.getErrorCount());
			return "home";
		}
		
		process(compForm, model);
		
		return "comp";
	}
	
	/**
	 * Preparing a defaults for form. If form values are null method search defaults in
	 * session, if session is null too method creates default values. 
	 * @param compForm
	 */
	private void prepareDefaults(CompForm compForm) {
		if (compForm.getListingDateFrom() == null) {
			if (userSession.getDateFrom() != null)
				compForm.setListingDateFrom(userSession.getDateFrom());
			else compForm.setListingDateFrom(LocalDate.now().minusMonths(3));
		}
		if (compForm.getListingDateTo() == null) {
			if (userSession.getDateTo() != null)
				compForm.setListingDateTo(userSession.getDateTo());
			else compForm.setListingDateTo(LocalDate.now());
		}
		if (compForm.getCountries() == null) {
			if (userSession.getCurriences() != null)
				compForm.setCountries(userSession.getCurriences());
			else
				compForm.setCountries(new ArrayList<>());
		}
	}
	
	/**
	 * Prepare data and store into model attrubutes.
	 * @param compForm
	 * @param model
	 */
	private void process(CompForm compForm, Model model) {
		// A table data.
		List<Map<String,Object>> tab = new ArrayList<>();
		// Check if time interval is correct
		if (compForm.getListingDateTo().isAfter(compForm.getListingDateFrom())) {
			// update session
			updateSession(compForm);
			// A list of tradings, used to create a charts.
			List<CurrencyTrading> list = new ArrayList<>();
			// Parse selected currencies
			for (String cur : compForm.getCountries()) {
				CurrencyTradingDAO dao;
				try {
					dao = new CurrencyTradingDAO(cur, compForm.getListingDateFrom(), compForm.getListingDateTo());
					dao.parse();
					CurrencyTrading t = dao.getCurrencyTrading();
					// add to tradings list
					list.add(t);
					// add table row
					tab.add(prepareTableRow(t));
				} catch (NbpDaoException e) {
					model.addAttribute("error",e.getMessage());
				}
			}
			// prepare chart data
			prepareCharts(list, model);
		} else {
			model.addAttribute("error","Date to must be after date from!");
			System.out.println("ERROR: Date to must be after date from!");
		}
		System.out.println(compForm);
		model.addAttribute("currList", currencies.getCurrenciessFull());
		model.addAttribute("list", tab);
	}
	
	/**
	 * Method updates a session values to current form values.
	 * @param compForm
	 */
	private void updateSession(CompForm compForm) {
		userSession.setDateFrom(compForm.getListingDateFrom());
		userSession.setDateTo(compForm.getListingDateTo());
		userSession.setCurriences(compForm.getCountries());
	}
	
	/**
	 * Prepare a charts data and add to model.
	 * @param list A tradings objects list
	 * @param model A model object
	 */
	private void prepareCharts(List<CurrencyTrading> list, Model model) {
		TradingChart tch = new TradingChart(list);
		model.addAttribute("chartsLabels",tch.getLabels());
		model.addAttribute("chartsDatas",tch.getData());
	}
	
	/**
	 * Preparing a single row from a trading object
	 * @param t Current trading object
	 * @return
	 */
	private Map<String,Object> prepareTableRow(CurrencyTrading t) {
		// A return object
		Map<String,Object> m = new HashMap<>();
		// count min and max
		double min = t.getRates().get(0).getBid();
		double max = min;
		for (Rate rate: t.getRates()) {
			if (rate.getBid() < min) min = rate.getBid();
			if (rate.getBid() > max) max = rate.getBid();
		}
		// Put a data
		m.put("currency", t.getCurrency());
		m.put("first", t.getRates().get(0).getBid());
		m.put("min", min);
		m.put("max", max);
		m.put("last", t.getRates().get(t.getRates().size()-1).getBid());
		// return map
		return m;
	}
}
