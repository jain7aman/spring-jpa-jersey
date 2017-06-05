package br.com.cinq.sample.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import br.com.cinq.sample.model.Country;

public class CountryDaoImplTest extends EntityDaoImplTest {
	
	@Autowired
	private CountryDao countryDao;

	/**
	 * The class extends from EntityDaoImplTest class, which loads data 
	 * into the database using 'city.xml' and 'country.xml' files present 
	 * under 'src/test/resources'
	 */
	
	/**
	 * Method tries to save new country into the database
	 */
	@Test
	public void testSaveCountry() {
		Country country = new Country("India");
		countryDao.saveCountry(country);
		Assert.assertNotNull(countryDao.getCountryIdByName("India"));
	}
	
	/**
	 * Method tries to fetch the countries stored in the database.
	 */
	@Test
	public void testGetAllCountries() {
		Assert.assertNotNull(countryDao.getAllCountries());
		Assert.assertNotEquals(countryDao.getAllCountries().size(), 0);
	}
	

	
	
	

}
