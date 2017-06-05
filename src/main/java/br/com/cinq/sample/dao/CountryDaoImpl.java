package br.com.cinq.sample.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.cinq.sample.model.Country;

@Repository("countryDao")
public class CountryDaoImpl extends AbstractDao<Integer, Country> implements CountryDao{

	public Long saveCountry(Country country) {
		Long countryId = getCountryIdByName(country.getName());
		if(countryId == null)
			saveOrUpdate(country);
		
		return countryId;
	}

	@SuppressWarnings("unchecked")
	public List<Country> getAllCountries() {
		Criteria criteria = createEntityCriteria();
		return (List<Country>) criteria.list();
	}
	
	public Long getCountryIdByName(String countryName) {
		Query query = getSession().createSQLQuery("Select id from country where name = :countryName");
		query.setString("countryName", countryName);
		if(null != query.uniqueResult())
			return ((BigInteger) query.uniqueResult()).longValue();
		else
			return null;
	}

}
