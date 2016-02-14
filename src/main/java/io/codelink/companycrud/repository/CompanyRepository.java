package io.codelink.companycrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.codelink.companycrud.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {}
