package br.com.cinq.sample.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cinq.sample.dao.CityDao;
import br.com.cinq.sample.model.City;
import br.com.cinq.sample.model.Country;

@Service("cityService")
@Transactional
public class CityServiceImpl implements CityService {
	
	@Autowired
	private CityDao cityDao;


	public void addCity(City city) {
		cityDao.addCity(city);
	}


	public List<City> getAllCities() {
		return cityDao.getAllCities();
	}


	public List<City> getCitiesMatchingCountry(String country) {
		return cityDao.getCitiesMatchingCountry(country);
	}


	public void loadCities(List<City> cities) {
		cityDao.saveAllCities(cities);
	}


	@Override
	public List<City> findByCountry(Country country) {
		return cityDao.findByCountryId(country);
	}


	@Override
	public int count() {
		return cityDao.count();
	}

}
