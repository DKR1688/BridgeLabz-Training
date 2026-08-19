package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.PasswordResetToken;
import com.bridgelabz.fundoonotes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByTokenHash(String tokenHash);
    void deleteByUser(User user);
}
