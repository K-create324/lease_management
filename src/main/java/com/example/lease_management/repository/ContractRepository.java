package com.example.lease_management.repository;

import com.example.lease_management.Client;
import com.example.lease_management.Contract;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ContractRepository extends CrudRepository<Contract,Integer> {
    List<Contract> findByclient(Client client);
    @EntityGraph(attributePaths = "items")

    @Query("SELECT c FROM Contract c LEFT JOIN FETCH c.items WHERE c.id = :id")
    Contract findWithItemsById(@Param("id") Integer id); //metoda która ładuje przedmiot od razu z kontraktem zeby uniknać lazy loading
    //normalnie ładowałby sie przedmiot dopiero wtedy gdyby był uzywany a to mogłobby być po zamknknieciu sesji hibernate
}
