package com.hoqi.practic20.repositories;

import com.hoqi.practic20.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface  ProductRepository extends JpaRepository<Product, Integer>{

}
