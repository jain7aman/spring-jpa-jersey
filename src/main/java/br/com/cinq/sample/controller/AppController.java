package br.com.cinq.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.cinq.sample.model.City;
import br.com.cinq.sample.service.CityService;
import br.com.cinq.sample.util.Utils;

@Controller
@RequestMapping("/")
public class AppController {
	
	@Autowired
	private CityService cityService;

	// GET: http://localhost:8080/rest/cities[?country=name]
	@RequestMapping(value = "/cities", method = RequestMethod.GET)
	public ResponseEntity<List<City>> getAllCities(@RequestParam(value = "country", required = false) String country) {
		List<City> cities = null;
		if(country == null) {
			cities = cityService.getAllCities();
		} else {
			cities = cityService.getCitiesMatchingCountry(country);
		}
		if(cities == null || cities.isEmpty()) {
			return new ResponseEntity<List<City>>(cities, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<City>>(cities, HttpStatus.OK);
	}
	
	// GET: http://localhost:8080/rest/loadSample
	@RequestMapping(value = "/loadSample", method = RequestMethod.GET)
	public ResponseEntity<List<City>> loadSampleCities() {
		String sampleDataFileName = "SampleData.csv";
		
		String filePath = Utils.getInstance().getSampleDataFilePath(sampleDataFileName);
	
		List<City> cities = Utils.getInstance().readDataFromFile(filePath);
		cityService.loadCities(cities);
	
		// check if cities are loaded properly
		cities = cityService.getAllCities();
		
		if(cities == null || cities.isEmpty()) {
			return new ResponseEntity<List<City>>(cities, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<City>>(cities, HttpStatus.OK);
	}
	
	// POST: http://localhost:8080/rest/load	
	@RequestMapping(value = "/load", method = RequestMethod.POST)
	public ResponseEntity<Void> loadCities(@RequestBody  List<City> cities) {
		cityService.loadCities(cities);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}


/*
	@RequestMapping(value = "/city", method = RequestMethod.POST)
	public ResponseEntity<Void> addCity(@RequestBody City city) {
		cityService.addCity(city);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}*/

}
