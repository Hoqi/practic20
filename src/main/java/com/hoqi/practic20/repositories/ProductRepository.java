package com.hoqi.practic20.repositories;

import com.hoqi.practic20.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ProductRepository extends JpaRepository<Product, Integer>{

}
