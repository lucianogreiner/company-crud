package io.codelink.companycrud.test;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.ALL;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.codelink.companycrud.CompanyCrud;
import io.codelink.companycrud.model.Address;
import io.codelink.companycrud.model.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(CompanyCrud.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompanyCrudTest {

	@Autowired
	private WebApplicationContext ctx;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void testInsertCompany() throws Exception {
		Company c = new Company();
		c.setName("CISCO");
		c.setEmail("cisco@cisco.com");
		c.setPhone("+0014343665");
		Address a = new Address();
		a.setCity("Pasadena");
		a.setStreet("Cisco Street");
		a.setCountry("United States");
		c.setAddress(a);
		c.setOwners(Arrays.asList("Named Owner 1", "Named Owner 2"));
		ObjectMapper mapper = new ObjectMapper();
		mvc.perform(post("/companies")
				.contentType(APPLICATION_JSON)
				.content(mapper.writeValueAsString(c))
        		.accept(ALL))
        		.andExpect(status().isCreated());
	}
	
	@Test
	public void testUpdateCompany() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		byte[] facebookData = mvc.perform(get("/companies/{id}", 5L)
        		.accept(APPLICATION_JSON))
        		.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsByteArray();
		Company facebook = mapper.readValue(facebookData, Company.class);
		Address a = facebook.getAddress();
		a.setCity("Menlo Park");
		mvc.perform(put("/companies/{id}", facebook.getId())
				.contentType(APPLICATION_JSON)
				.content(mapper.writeValueAsString(facebook))
        		.accept(ALL))
        		.andExpect(status().isAccepted());
	}
	
	@Test
	public void testAddCompanyOwner() throws Exception {
		mvc.perform(put("/companies/{id}/owner", 5L)
				.param("name", "Eduardo Severin")
				.contentType(APPLICATION_FORM_URLENCODED)
        		.accept(ALL))
        		.andExpect(status().isAccepted());
		mvc.perform(get("/companies/{id}", 5L)
        		.accept(APPLICATION_JSON))	
				.andExpect(status().isOk())
        		.andExpect(jsonPath("$").isMap())
        		.andExpect(jsonPath("$.owners").isArray())
        		.andExpect(jsonPath("$.owners", hasSize(2)));
	}
	
	@Test
	public void testListCompanies() throws Exception {
		mvc.perform(get("/companies")
        		.accept(APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$").isArray())
        		.andExpect(jsonPath("$", hasSize(6)))
        		.andExpect(jsonPath("$[?(@.name == '%s')]", "GOOGLE").exists())
        		.andExpect(jsonPath("$[?(@.address.city == '%s')]", "Pasadena").exists())
        		.andExpect(jsonPath("$[?(@.name == '%s')]", "SAP").doesNotExist());
	}
	
	@Test
	public void testLoadCompany() throws Exception {
		mvc.perform(get("/companies/{id}", 1L)
        		.accept(APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$").isMap())
        		.andExpect(jsonPath("$.name").value("GOOGLE"))
        		.andExpect(jsonPath("$.address.city").value("Palo Alto"))
        		.andExpect(jsonPath("$.address.country").value("United States"));
		mvc.perform(get("/companies/{id}", 10L)
        		.accept(APPLICATION_JSON))
        		.andExpect(status().isNotFound());
		mvc.perform(get("/companies/{id}", 2L)
        		.accept(APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$").isMap())
        		.andExpect(jsonPath("$.owners").isArray())
        		.andExpect(jsonPath("$.owners[?(@ == '%s')]", "Larry Ellison").exists());
	}
		
}
