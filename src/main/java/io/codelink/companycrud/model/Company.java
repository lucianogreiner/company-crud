package io.codelink.companycrud.model;

import io.codelink.companycrud.CompanyCrud.SummaryView;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "TB_COMPANY")
public class Company implements Serializable {

	private static final long serialVersionUID = -5647343566193937792L;

	@Id
	@Column(name = "COMPANY_ID")
	@GeneratedValue(generator = "COMPANY_ID_GEN", strategy = SEQUENCE)
	@SequenceGenerator(name = "COMPANY_ID_GEN", sequenceName = "SQ_COMPANY")
	@JsonView(SummaryView.class)
	private Long id;

	@Column(name = "COMPANY_NAME")
	@JsonView(SummaryView.class)
	@Size(min = 5, max = 100)
	private String name;

	@Embedded
	@JsonView(SummaryView.class)
	@NotNull
	private Address address;

	@Column(name = "COMPANY_EMAIL")
	@JsonView(SummaryView.class)
	@Size(max=100)
	private String email;

	@Column(name = "COMPANY_PHONE")
	@Size(max=20)
	private String phone;

	@ElementCollection
	@CollectionTable(name = "TB_COMPANY_OWNER", joinColumns = @JoinColumn(name = "COMPANY_ID") )
	@Column(name = "OWNER_NAME")
	@OrderColumn(name = "OWNER_ORDER")
	@Size(min=1, message="At least one owner is required")
	private List<String> owners;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<String> getOwners() {
		return owners;
	}

	public void setOwners(List<String> owners) {
		this.owners = owners;
	}

}
