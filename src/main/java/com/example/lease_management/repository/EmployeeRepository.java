package com.example.lease_management.repository;

import com.example.lease_management.logg.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Integer> {
    Employee findByEmail(String email);
}
