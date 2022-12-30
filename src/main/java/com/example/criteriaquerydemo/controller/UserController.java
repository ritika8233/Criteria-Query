package com.example.criteriaquerydemo.controller;

import com.example.criteriaquerydemo.dto.SpecifiactionDto;
import com.example.criteriaquerydemo.dto.SpecificationRequest;
import com.example.criteriaquerydemo.model.User;
import com.example.criteriaquerydemo.repository.UserRepository;
import com.example.criteriaquerydemo.service.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired UserRepository userRepository;
    @Autowired UserService<User> userService;

    @RequestMapping(value = "/firstname/{firstname}", method = RequestMethod.GET)
    public User getByfirstName(@PathVariable String firstname){
        return userRepository.findByFirstname(firstname);
    }

    @RequestMapping(value = "/price/{price}", method = RequestMethod.GET)
    public List<User> getByPrice(@PathVariable Long price){
        return userRepository.findByPrice(price);
    }

    @RequestMapping(value = "/specification", method = RequestMethod.POST)
    public List<User> getBySpecification(@RequestBody SpecifiactionDto specifiactionDto){
        Specification<User> specifiaction = userService.getBySpecifiaction(specifiactionDto);
        return userRepository.findAll(specifiaction);
    }

    @RequestMapping(value = "/specificationList", method = RequestMethod.POST)
    public List<User> getBySpecificationList(@RequestBody SpecificationRequest specificationRequest){
        Specification<User> specifiaction = userService.getBySpecifiactions(specificationRequest.getList(), specificationRequest.getOperator());
        return userRepository.findAll(specifiaction);
    }


    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public User saveUser(@RequestBody User user){
        userRepository.save(user);
        return user;
    }
}
