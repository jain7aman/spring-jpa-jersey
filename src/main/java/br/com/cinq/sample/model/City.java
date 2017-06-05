package br.com.cinq.sample.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "city", uniqueConstraints = @UniqueConstraint(columnNames={"name", "country_id"}))
public class City {

	private long id;
	private String name;
	public Country country;

	public City() {
	}

	public City(String name, Country country) {
		this.name = name;
		this.country = country;
	}

	@Id
	@GeneratedValue
	@Column(name = "id")
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(cascade = {CascadeType.ALL},fetch= FetchType.EAGER)
	@JoinColumn(name = "country_id", referencedColumnName = "id")
	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}