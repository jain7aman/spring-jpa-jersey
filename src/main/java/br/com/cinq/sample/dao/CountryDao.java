package br.com.cinq.sample.dao;

import java.util.List;

import br.com.cinq.sample.model.Country;

public interface CountryDao {

	/**
	 * saves the country into the data base
	 * @param country: country to be stored into the database
	 * @return returns ID of the country if it already exits, otherwise null
	 */
	Long saveCountry(Country country);

	/**
	 * Will be used to retrieve the list of all countries
	 * @return a list of all the countries
	 */
	List<Country> getAllCountries();

	
	/**
	 * Returns the ID of the country, if present
	 * @param countryName
	 * @return ID of country if present in the database, otherwise null
	 */
	Long getCountryIdByName(String countryName);
}
