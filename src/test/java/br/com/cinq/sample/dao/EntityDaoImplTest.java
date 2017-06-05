package br.com.cinq.sample.dao;

import javax.sql.DataSource;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;

import br.com.cinq.sample.configuration.HibernateTestConfiguration;

@ContextConfiguration(classes = { HibernateTestConfiguration.class })
public abstract class EntityDaoImplTest  extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	DataSource dataSource;

	@BeforeMethod
	public void setUp() throws Exception {
		IDatabaseConnection dbConn = new DatabaseDataSourceConnection(
				dataSource);
		DatabaseOperation.CLEAN_INSERT.execute(dbConn, getDataSet());
	}

	protected IDataSet getDataSet() throws Exception {
		/*DefaultTable cityTable = new DefaultTable("city",
				new Column[] { 
						new Column("id", DataType.BIGINT),
						new Column("name", DataType.VARCHAR),
						new Column("country_id",  DataType.BIGINT),	            
		});

		DefaultTable countryTable = new DefaultTable("country",
				new Column[] { 
						new Column("id", DataType.BIGINT),
						new Column("name", DataType.VARCHAR)	            
		});

		List<City> cities = Utils.getInstance().readDataFromFile("C:\\Users\\amanjain\\Desktop\\Book1.csv");

		if(cities != null) {
			for (City city : cities) {
				cityTable.addRow(new Object[] { city.getId(), city.getName(), city.getCountry().getId()});
				countryTable.addRow(new Object[] {city.getCountry().getId(), city.getCountry().getName()});
			}
		}



		IDataSet[] dataSets =   new IDataSet[] {
				new DefaultDataSet(countryTable), 
				new DefaultDataSet(cityTable)
		};*/

		@SuppressWarnings("deprecation")
		IDataSet[] dataSets = new IDataSet[] {
				new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("country.xml")),
				new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("city.xml"))
		};

		return new CompositeDataSet(dataSets);
	}
}
