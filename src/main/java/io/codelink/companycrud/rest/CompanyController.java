package io.codelink.companycrud.rest;

import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import io.codelink.companycrud.model.Company;
import io.codelink.companycrud.CompanyCrud.SummaryView;
import io.codelink.companycrud.repository.CompanyRepository;

@RestController
@RequestMapping("/companies")
public class CompanyController {

	@Autowired
	private CompanyRepository repository;	

	@RequestMapping(method=GET)
	@JsonView(SummaryView.class)
	@ResponseBody
	public List<Company> listCompanies() {
		return repository.findAll();
	}
	
	@RequestMapping(method=POST)
	public ResponseEntity<?> insertCompany(@RequestBody @Validated Company company) {
		company.setId(null);
		Company saved = repository.save(company);	
		URI location = fromPath("/companies/{id}").buildAndExpand(saved.getId()).toUri();
		return created(location).build();
	}
	
	@RequestMapping(path="/{id:\\d+}", method=PUT)
	public ResponseEntity<?> updateCompany(@PathVariable("id") Long id, @RequestBody @Validated Company company) {
		if(!repository.exists(id)) {
			return NOT_FOUND;
		}
		company.setId(id);
		repository.save(company);
		return ACCEPTED;
	}
		
	@RequestMapping(path="/{id:\\d+}", method=GET)
	public ResponseEntity<?> loadCompany(@PathVariable("id") Long id) {
		Company company = repository.findOne(id);
		return company == null ? NOT_FOUND : ok(company);
	}
	
	@RequestMapping(path="/{id:\\d+}/owner", method=PUT)
	public ResponseEntity<?> addOwner(@PathVariable("id") Long id, @RequestParam("name") String name) {
		Company company = repository.findOne(id);
		if(company == null) {
			return NOT_FOUND;
		}
		company.getOwners().add(name);
		return updateCompany(id, company);
	}

	private static final ResponseEntity<?> NOT_FOUND = notFound().build();
	private static final ResponseEntity<?> ACCEPTED = accepted().build();
	
}