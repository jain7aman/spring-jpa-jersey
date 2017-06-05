package br.com.cinq.sample.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import br.com.cinq.sample.configuration.AppConfiguration;
import br.com.cinq.sample.model.City;
import br.com.cinq.sample.model.Country;
import br.com.cinq.sample.util.Utils;

@WebAppConfiguration
@ContextConfiguration(classes = {AppConfiguration.class})
public class AppControllerTest extends AbstractTransactionalTestNGSpringContextTests  {

	@Autowired
	private AppController appController;
	
	private final String sampleDataFileName = "SampleData.csv";
	
	/**
	 * Method is used to test REST GET end-point:
	 * 		http://localhost:8080/rest/loadSample 
	 * It tries to load data from the 'SampleData.csv' file using controller
	 * and then matches the number of items in the list of cities returned by 
	 * the controller and the one present in the file.
	 */
	@Test
	public void testLoadData() {
		Assert.assertNotNull(appController);
		
		ResponseEntity<List<City>> response = appController.loadSampleCities();
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(response.getBody().size(), getCityList().size());
	}
	
	/**
	 * Method is used to test 'idempotent' property of insertion maintained
	 * by the service. Insertion of newly already existing 'city-country'
	 * combination does not add new entries into the database.
	 */
	@Test
	public void testDualLoadData() {
		Assert.assertNotNull(appController);
	
		Assert.assertNotNull(appController.loadSampleCities());
		Assert.assertNotNull(appController.loadSampleCities());
		
		// get all cities
		String country = null;
		ResponseEntity<List<City>> response =  appController.getAllCities(country);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		List<City> cities = response.getBody();
		Assert.assertEquals(getCityList().size(), cities.size());
	}
	
	
	/**
	 * Method is testing REST GET endpoint:
	 * 		http://localhost:8080/rest/cities?country=non-matching-country
	 * Here, we provide a country which does not exist in the database and
	 * then check the size of the city list returned. It should be 0.
	 */
	@Test
	public void testNoMatchingCountry() {
		Assert.assertNotNull(appController);
		Assert.assertNotNull(appController.loadSampleCities());
		
		String country = "non-matching-country";
		ResponseEntity<List<City>> response =  appController.getAllCities(country);
		Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		List<City> cities = response.getBody();
		Assert.assertEquals(0, cities.size());
	}
	
	/**
	 * Method is testing REST GET endpoint:
	 * 		http://localhost:8080/rest/cities?country=France
	 * Here, we are providing the country which exist in our database [loaded 
	 * from 'SampleData.csv' file]. The database has two cities corresponding to
	 * 'France' country. So the size of city list returned should be 2.
	 */
	@Test
	public void testGetCities() {
		ResponseEntity<List<City>> response = appController.loadSampleCities();
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(response.getBody().size(), getCityList().size());
		
		
		String country = "France";
		response =  appController.getAllCities(country);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		List<City> cities = response.getBody();
		Assert.assertEquals(2, cities.size());
	}
	
	
	/**
	 * Method is testing the REST POST end-point:
	 * 		http://localhost:8080/rest/load
	 * here, we are trying to add new city-country combination into
	 * out database. Then we test by querying city list for the newly
	 * added country. The size of list returned should be 1.
	 */
	@Test
	public void testAddNewCities() {
		Country country = new Country("India");
		City city = new City("Bangalore", country);
		List<City> cities = new ArrayList<City>();
		cities.add(city);
		
		ResponseEntity<Void> response =  appController.loadCities(cities);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		
		String countryName = "Indi"; //sending only part of the country name 
		ResponseEntity<List<City>> newResponse =  appController.getAllCities(countryName);
		Assert.assertEquals(HttpStatus.OK, newResponse.getStatusCode());
		cities = newResponse.getBody();
		Assert.assertEquals(1, cities.size());
	}

	/**
	 * Method is used to return the list of cities after reading it from
	 * 'SampleData.csv' file present at 'src/test/resources'
	 * @return List of cities
	 */
	public List<City> getCityList() {
		File resourcesDirectory = new File("src/test/resources");
		String dataFilePath = resourcesDirectory.getAbsolutePath() + "/" + sampleDataFileName; 
		return Utils.getInstance().readDataFromFile(dataFilePath);
	}
}
