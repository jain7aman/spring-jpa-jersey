package br.com.cinq.sample.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.cinq.sample.model.City;
import br.com.cinq.sample.model.Country;

@Repository("cityDao")
public class CityDaoImpl extends AbstractDao<Integer, City> implements CityDao{
	@Autowired
	private CountryDao countryDao;
	
	public void addCity(City city) {
		Long countryId = countryDao.saveCountry(city.getCountry());
		
		// avoiding duplicate entries into the database
		if(countryId != null) {
			Country country = city.getCountry();
			country.setId(countryId);
			city.setCountry(country);
		}
		
		Long cityId = getCityId(city.getName(), city.getCountry().getId());
		if(cityId == null) // if city isn't already in the database
			save(city);
			
	}

	@SuppressWarnings("unchecked")
	public List<City> getAllCities() {
		Criteria criteria = createEntityCriteria();
		return (List<City>) criteria.list();
	}

	public List<City> getCitiesMatchingCountry(String country) {
		Criteria criteria = getSession().createCriteria(City.class);
		@SuppressWarnings("unchecked")
		List<City> list =
				criteria.createCriteria("country", "con", JoinType.INNER_JOIN,
						Restrictions.ilike("con.name", country, MatchMode.ANYWHERE)).list();
		return list;
	}

	public void saveAllCities(List<City> cities) {
		for(City city : cities){
			addCity(city);
		}
	}
	
	private Long getCityId(String cityName, long countryId) {
		Query query = getSession().createSQLQuery("Select id from city where name = :cityName and country_id = :countryId");
		query.setString("cityName", cityName);
		query.setLong("countryId", countryId);
		if(null != query.uniqueResult())
			return ((BigInteger) query.uniqueResult()).longValue();
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<City> findByCountryId(Country country) {
		long countryId = country.getId();
		
		Query query = getSession().createSQLQuery("Select * from city where country_id = :countryId");
		query.setLong("countryId", countryId);
		
		return (List<City>) query.uniqueResult();
	}

	@Override
	public int count() {
		return (int) getSession().createCriteria(City.class).setProjection(Projections.rowCount()).uniqueResult();
	}

}
