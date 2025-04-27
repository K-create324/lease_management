package com.example.lease_management.repository;

import com.example.lease_management.logg.ExternalUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalUserRepository extends CrudRepository<ExternalUser,Integer> {
    ExternalUser findByEmail(String email);
}
