package com.example.user.management.Repository;

import com.example.user.management.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    String deleteById(int id);
    User findUsersByEmail(String email);

}
