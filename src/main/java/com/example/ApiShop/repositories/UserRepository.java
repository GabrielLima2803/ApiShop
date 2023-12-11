package com.example.ApiShop.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ApiShop.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
