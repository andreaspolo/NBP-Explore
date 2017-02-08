package com.pat.nbp.explore.controller;


import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pat.nbp.explore.dao.CurrenciesTradingDAO;
import com.pat.nbp.explore.dao.NbpDaoException;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/")
	public String home(@Valid HomeForm homeForm, BindingResult br, Model model) {

		if (br.hasErrors()) {
			System.out.println("FOUND ERRORS: " + br.getErrorCount());
			return "home";
		}

		CurrenciesTradingDAO dao = new CurrenciesTradingDAO(homeForm.getListingDate());
		try {
			dao.parse();
			model.addAttribute("trading",dao.getCurrenciesTrading());
		} catch (NbpDaoException e) {
			model.addAttribute("error",e.getMessage());
		}
		
		return "home";
	}
	
	@RequestMapping(value = "/contact")
	public String contact() {
		return "contact";
	}
	
	

	

}
