package br.com.cinq.sample.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.com.cinq.sample.dao.CityDao;
import br.com.cinq.sample.model.City;

public class CityServiceImplTest {

	@Mock
	private CityDao cityDao;
	
	@InjectMocks
	private CityServiceImpl cityService; 
	
	@Spy
	private List<City> cities = new ArrayList<City>();
	
	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Method tries to test the fetching capabilities of service class
	 * based on mock data
	 */
	@Test
    public void testFindByCountryName(){
		List<City> cityList = cities;
		String countryName;
		when(cityDao.getCitiesMatchingCountry(countryName = anyString())).thenReturn(cityList);
		Assert.assertEquals(cityService.getCitiesMatchingCountry(countryName), cityList);
    }
	
	/**
	 * Method tries to add new city using service class
	 */
	@Test
	public void testAddCity() {
		doNothing().when(cityDao).addCity(any(City.class));
		cityService.addCity(any(City.class));
		verify(cityDao, atLeastOnce()).addCity(any(City.class));
	}
	
	/**
	 * Method tries to retrieve all the cities present in the database (mocked)
	 * using service class.
	 */
	@Test
	public void testGetAllCities() {
		when(cityDao.getAllCities()).thenReturn(cities);
		Assert.assertEquals(cityService.getAllCities(), cities);
	}
	
}
