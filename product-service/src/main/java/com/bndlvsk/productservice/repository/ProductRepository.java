package com.bndlvsk.productservice.repository;


import com.bndlvsk.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

    List<Product> findAllByCategoryId(Long categoryId);

    boolean existsByName(String name);
}
