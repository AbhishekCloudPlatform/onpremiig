package com.iig.gcp.usermanagement.dao;

import org.springframework.data.repository.CrudRepository;

import com.iig.gcp.usermanagement.dto.User;

public interface UserRepository extends CrudRepository<User, Integer>{

}
