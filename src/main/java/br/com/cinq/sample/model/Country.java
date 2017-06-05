package br.com.cinq.sample.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "country", uniqueConstraints = @UniqueConstraint(columnNames={"name"}))
public class Country {

	private long id;
	private String name;
	

	public Country() {
	}

	public Country(String name) {
		this.name = name;
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
	
	@Column(name = "name", nullable = false, length=250)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}