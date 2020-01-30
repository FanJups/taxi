package biz.advanceitgroup.taxirideserver.authentification.repositories;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.authentification.entities.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
	Optional<EmailVerificationToken> findByToken(String token);
	EmailVerificationToken findByUser(Users user);
}
