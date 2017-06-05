package br.com.cinq.sample.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.cinq.sample.model.City;
import br.com.cinq.sample.model.Country;

public class Utils {
	

	private volatile static Utils _instance;
	private Utils() {
	}
	
	
	
	public static Utils getInstance() {
        if (_instance == null) {
            synchronized (Utils.class) {
                if (_instance == null) {
                    _instance = new Utils();
                }
            }
        }
        return _instance;
    }

	public List<City> readDataFromFile(String csvFile) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        Country country = null;
        City city = null;
        List<City> cities = new ArrayList<City>();
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] row = line.split(cvsSplitBy);

//                System.out.println("[city= " + row[0] + " , country=" + row[1] + "]");
                
                if(row.length == 2) {
                	country = new Country(row[1]);
                	city = new City(row[0], country);
                	cities.add(city);
                	city = null;
                	country = null;
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return cities;
	}
	
	public String getSampleDataFilePath(String sampleDataFileName) {
//		File resourcesDirectory = new File("src/main/resources");
//		return resourcesDirectory.getAbsolutePath() + "/" + sampleDataFileName; 
		return this.getClass().getClassLoader().getResource(sampleDataFileName).getPath();
	}

	
	
}
