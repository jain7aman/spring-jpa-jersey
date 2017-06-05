package br.com.cinq.sample.service;

import java.util.List;

import br.com.cinq.sample.model.City;
import br.com.cinq.sample.model.Country;

public interface CityService {
	
	/**
	 * Method is used to add a new city into the database
	 * @param city: the city to be added
	 */
	void addCity(City city);
	
	/**
	 * Method is use to save bulk of cities 
	 * @param cities
	 */
	void loadCities(List<City> cities);
	
	/**
	 * Method is used to return the list of the cities present in the database
	 * @return list of all cities
	 */
	List<City> getAllCities();
	
	/**
	 * get list of cities whose countries matches with the given country name
	 * @param country full or part of country name
	 * @return list of cities matching the country name
	 */
	List<City> getCitiesMatchingCountry(String country);
	
	/**
	 * find the list of cities in the given country, if any
	 * @param country
	 * @return List of cities in the country
	 */
	List<City> findByCountry(Country country);
	
	/**
	 * Method to return the count of total cities
	 * @return number of cities present in the database
	 */
	int count();
}
