package br.com.cinq.sample.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import br.com.cinq.sample.model.City;
import br.com.cinq.sample.model.Country;

public class CityDaoImplTest extends EntityDaoImplTest {
	
	@Autowired
	private CityDao cityDao;
	
	/**
	 * The class extends from EntityDaoImplTest class, which loads data 
	 * into the database using 'city.xml' and 'country.xml' files present 
	 * under 'src/test/resources'
	 */
	
	/**
	 * Method test the fetching capabilities of the DAO class, given the
	 * country name.
	 */
	@Test
    public void testFindByName(){
        Assert.assertNotNull(cityDao.getCitiesMatchingCountry("France"));
        Assert.assertEquals(cityDao.getCitiesMatchingCountry("France").size(), 2);
        Assert.assertEquals(cityDao.getCitiesMatchingCountry("newCountry").size(), 0);
    }
	
	/**
	 * Method adds new 'city-country' combination into the database and then
	 * verifies the added value
	 */
	@Test
	public void testSaveCity() {
		Country country = new Country("India");
		City city = new City("Bangalore", country);
		
		cityDao.addCity(city);
		Assert.assertNotNull(cityDao.getCitiesMatchingCountry("India"));
        Assert.assertEquals(cityDao.getCitiesMatchingCountry("India").size(), 1);
        Assert.assertEquals(cityDao.getCitiesMatchingCountry("India").get(0).getName(), "Bangalore");
	}
	
	/**
	 * Method is used to test fetching capabilities of the DAO class
	 * without any filtering condition (of optional country parameter). 
	 */
	@Test
	public void testGetAllCities() {
		List<City> cities = cityDao.getAllCities();
		Assert.assertNotNull(cities);
		Assert.assertNotEquals(cities.size(), 0);
	}
	
	/**
	 * Method test the fetching capabilities of the DAO class when
	 * part of the country name is given in the input.
	 */
	@Test
	public void testGetMatchingCities() {
		List<City> cities = cityDao.getCitiesMatchingCountry("zi"); // Brazil
		Assert.assertNotNull(cities);
		Assert.assertEquals(cities.size(), 4);
	}
	
	
	/**
	 * Insertion should be idempotent, duplicate cities 
	 * (belonging to same country) should not be added again
	 */
	@Test
	public void testInsertDuplicates() {
		Country country = new Country("Brazil");
		City city = new City("Rio de Janeiro", country);
		cityDao.addCity(city);
		
		Assert.assertNotNull(cityDao.getCitiesMatchingCountry("Brazil"));
        Assert.assertEquals(cityDao.getCitiesMatchingCountry("Brazil").size(), 4);
	}

}
