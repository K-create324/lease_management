package com.example.lease_management.repository;

import com.example.lease_management.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item,Integer> {
}
