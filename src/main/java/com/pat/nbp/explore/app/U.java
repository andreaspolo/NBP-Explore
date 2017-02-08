package com.pat.nbp.explore.app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * A helper class
 * 
 * @author Andrzej Poloczek
 * @version 1.0
 */
public class U {

	/**
	 * A DateTimeFormatter of pattern used in <b>api.nbp.pl</b>.
	 */
	public static final DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	/**
	 * Validating a string to valid date, used in <b>api.nbp.pl</b>
	 * 
	 * @param date Date to valid in as string
	 * @param defaultDate Default date if validation will fail.
	 * @return
	 */
	public static LocalDate toValidDate(String date, LocalDate defaultDate) {
		if (date != null && !"".equals(date)) {
			try {
				return LocalDate.parse(date, U.f);
			} catch (DateTimeParseException ex) {
				System.out.println(ex.getMessage());
				return defaultDate;
			}
		} else {
			return defaultDate;
		}
	}
	
	/**
	 * Validating a string to valid date, used in <b>api.nbp.pl</b>. If default
	 * date is null method returns empty string.
	 * 
	 * @param date Date to valid in as string
	 * @param defaultDate Default date if validation will fail.
	 * @return
	 */
	public static String toValidString(String date, LocalDate defaultDate) {
		
		String _date;
		
		if (date != null && !"".equals(date)) {
			try {
				_date = LocalDate.parse(date, U.f).format(U.f);
			} catch (DateTimeParseException ex) {
				System.out.println(ex.getMessage());
				_date = (defaultDate!=null)?defaultDate.format(U.f):"";
			}
		} else {
			_date = (defaultDate!=null)?defaultDate.format(U.f):"";
		}
		
		return _date;
	}
}
