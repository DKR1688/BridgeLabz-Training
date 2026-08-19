package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.notes WHERE u.userId = :userId")
    Optional<User> findByIdWithNotes(@Param("userId") int userId);
}
