package com.pat.nbp.explore.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pat.nbp.explore.app.Currencies;
import com.pat.nbp.explore.app.U;
import com.pat.nbp.explore.dao.CurrencyTrading;
import com.pat.nbp.explore.dao.CurrencyTradingDAO;
import com.pat.nbp.explore.dao.NbpDaoException;
import com.pat.nbp.explore.dao.TradingChart;
import com.pat.nbp.explore.user.UserSession;

@Controller
public class AnalizeController {

	@Autowired
	private Currencies currencies;
	
	@Autowired
	private UserSession userSession;
	
	
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/analize", method = RequestMethod.GET)
	public String analizeGet(@Valid AnalizeForm analizeForm, BindingResult br, Model model) {
		
		prepareDefaults(analizeForm);
		updateSession(analizeForm);
		
		// Get data for defaults
		CurrencyTrading trading;
		try {
			trading = getCurrencyTrading(
					analizeForm.getCountry(), 
					analizeForm.getListingDateFrom(), 
					analizeForm.getListingDateTo());
			// Save model
			toModel(
					model, trading, 
					analizeForm.getListingDateFrom().format(U.f), 
					analizeForm.getListingDateTo().format(U.f), 
					analizeForm.getCountry());
		} catch (NbpDaoException e) {
			model.addAttribute("country", analizeForm.getCountry());
			model.addAttribute("error",e.getMessage());
		}
		
		

		// return view
		return "analize";
	}
	
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/analize", method = RequestMethod.POST)
	public String analizePost(@Valid AnalizeForm analizeForm, BindingResult br, Model model) {
		
		prepareDefaults(analizeForm);
		
		if (br.hasErrors()) {
			System.out.println("FOUND ERRORS: " + br.getErrorCount());
			return "analize";
		}
		
		updateSession(analizeForm);
		
		// Get data
		CurrencyTrading trading;
		try {
			trading = getCurrencyTrading(
					analizeForm.getCountry(), 
					analizeForm.getListingDateFrom(), 
					analizeForm.getListingDateTo());
			// Save model
			toModel(
					model, trading, 
					analizeForm.getListingDateFrom().format(U.f), 
					analizeForm.getListingDateTo().format(U.f), 
					analizeForm.getCountry());
		} catch (NbpDaoException e) {
			model.addAttribute("country", analizeForm.getCountry());
			model.addAttribute("error",e.getMessage());
		}
		
		//return view
		return "analize";
	}
	
	/**
	 * Preparing a defaults for form. If form values are null method search defaults in
	 * session, if session is null too method creates default values. 
	 * @param compForm
	 */
	private void prepareDefaults(AnalizeForm analizeForm) {
		if (analizeForm.getListingDateFrom() == null) {
			if (userSession.getDateFrom() != null)
				analizeForm.setListingDateFrom(userSession.getDateFrom());
			else analizeForm.setListingDateFrom(LocalDate.now().minusMonths(3));
		}
		if (analizeForm.getListingDateTo() == null) {
			if (userSession.getDateTo() != null)
				analizeForm.setListingDateTo(userSession.getDateTo());
			else analizeForm.setListingDateTo(LocalDate.now());
		}
		if (analizeForm.getCountry() == null) {
			if (userSession.getCurrency() != null && !userSession.getCurrency().isEmpty())
				analizeForm.setCountry(userSession.getCurrency());
			else
				analizeForm.setCountry("EUR");
		}
	}
	
	
	/**
	 * Method updates a session values to current form values.
	 * @param compForm
	 */
	private void updateSession(AnalizeForm analizeForm) {
		userSession.setDateFrom(analizeForm.getListingDateFrom());
		userSession.setDateTo(analizeForm.getListingDateTo());
		userSession.setCurrency(analizeForm.getCountry());
	}
	
	
	/**
	 * Getting a currency trading from api.nbp.pl via data access object <b>CurrencyTradingDAO</b>.
	 * @param currency A currency code
	 * @param from A date from
	 * @param to A date to
	 * @return
	 * @throws NbpDaoException 
	 */
	private CurrencyTrading getCurrencyTrading(String currency, LocalDate from, LocalDate to) throws NbpDaoException {
		CurrencyTradingDAO dao = new CurrencyTradingDAO(currency, from, to);
		dao.parse();
		return dao.getCurrencyTrading();
	}
	
	
	/**
	 * Setting data into model. It is separated from action methods to better manage with attrubites names.
	 * @param model
	 * @param trading
	 * @param from
	 * @param to
	 * @param country
	 */
	private void toModel(Model model, CurrencyTrading trading, String from, String to, String country) {
		
		// prepare chart data
		TradingChart tch = new TradingChart(trading);

		// Set model
		model.addAttribute("chartsLabels", tch.getLabels());
		model.addAttribute("chartsDatas",  tch.getData());
		model.addAttribute("trading",trading);
		model.addAttribute("listingDateFrom", from);
		model.addAttribute("listingDateTo", to);		
		model.addAttribute("currList", currencies.getCurrenciessFullAsChooser());
		model.addAttribute("country", country);
	}
	
	
	
	
	
	
}
