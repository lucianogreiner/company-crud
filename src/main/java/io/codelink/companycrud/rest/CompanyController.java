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

import io.codelink.companycrud.CompanyCrud.SummaryView;
import io.codelink.companycrud.model.Company;
import io.codelink.companycrud.repository.CompanyRepository;


/**
 * CompanyController handles REST requests to /companies crud operations
 * 
 * @author Luciano Greiner (luciano.greiner@gmail.com)
 */
@RestController
@RequestMapping("/companies")
public class CompanyController {

	@Autowired
	private CompanyRepository repository;	

	/**
	 * List all registered companies.
	 * 
	 * @return List of Company objects with summarized data (id, name, email, city and country)
	 * 
	 * @see Company
	 * 
	 */
	@RequestMapping(method=GET)
	@JsonView(SummaryView.class)
	@ResponseBody
	public List<Company> listCompanies() {
		return repository.findAll();
	}

	/**
	 * Insert a new company record.
	 * 
	 * @param Company object to be inserted
	 * 
	 * @return ResponseEntity with CREATED status and location header containing URL to the inserted company.
	 * 
	 * @see Company
	 * 
	 */
	@RequestMapping(method=POST)
	public ResponseEntity<?> insertCompany(@RequestBody @Validated Company company) {
		company.setId(null);
		Company saved = repository.save(company);	
		URI location = fromPath("/companies/{id}").buildAndExpand(saved.getId()).toUri();
		return created(location).build();
	}
	
	
	/**
	 * Updtes a new company record.
	 * 
	 * @param ID of company record to be updated
	 * @param Company object to be updated
	 * 
	 * @return ResponseEntity with ACCEPTED status if company has been updated, or NOT_FOUND in case company does not exist.
	 * 
	 * @see Company
	 * 
	 */
	@RequestMapping(path="/{id:\\d+}", method=PUT)
	public ResponseEntity<?> updateCompany(@PathVariable("id") Long id, @RequestBody @Validated Company company) {
		if(!repository.exists(id)) {
			return NOT_FOUND;
		}
		company.setId(id);
		repository.save(company);
		return ACCEPTED;
	}
	
	/**
	 * Loads new company record.
	 * 
	 * @param ID of company record to be loaded
	 *
	 * @return ResponseEntity with OK status and company record body, or NOT_FOUND in case company does not exist.
	 * 
	 * @see Company
	 * 
	 */
	@RequestMapping(path="/{id:\\d+}", method=GET)
	public ResponseEntity<?> loadCompany(@PathVariable("id") Long id) {
		Company company = repository.findOne(id);
		return company == null ? NOT_FOUND : ok(company);
	}
	
	/**
	 * Adds an owner to a company record.
	 * 
	 * @param ID of company record to be updated
	 * @param NAME of the owner to be added to the company record
	 *
	 * @return ResponseEntity with ACCEPTED status if company has been updated, or NOT_FOUND in case company does not exist.
	 * 
	 * @see Company
	 * 
	 */
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