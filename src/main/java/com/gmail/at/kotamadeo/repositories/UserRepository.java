package com.gmail.at.kotamadeo.repositories;

import com.gmail.at.kotamadeo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail (String email);
}
