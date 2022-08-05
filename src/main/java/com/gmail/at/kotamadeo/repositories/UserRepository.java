package com.gmail.at.kotamadeo.repositories;

import com.gmail.at.kotamadeo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u from User u join fetch u.roles where u.email = :email")
    User findByEmail(@Param("email") String email);
}
