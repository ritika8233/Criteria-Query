package com.example.criteriaquerydemo.repository;

import com.example.criteriaquerydemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByFirstname(String firstname);

    List<User> findByPrice(Long price);

}
