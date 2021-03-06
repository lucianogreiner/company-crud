package io.codelink.companycrud.model;

import io.codelink.companycrud.CompanyCrud.SummaryView;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

@Embeddable
public class Address implements Serializable {

	private static final long serialVersionUID = 2904513790573148667L;

	@Column(name="ADDRESS_STREET")
	@NotNull
	@Size(max=100)
	private String street;
	
	@Column(name="ADDRESS_CITY")
	@JsonView(SummaryView.class)
	@NotNull
	@Size(max=100)
	private String city;
	
	@Column(name="ADDRESS_COUNTRY")
	@JsonView(SummaryView.class)
	@NotNull
	@Size(max=100)
	private String country;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
