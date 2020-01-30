package biz.advanceitgroup.taxirideserver.authentification.repositories;

import biz.advanceitgroup.taxirideserver.authentification.entities.PhoneVerificationToken;
import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneVerificationTokenRepository extends JpaRepository<PhoneVerificationToken, Long> {
    Optional<PhoneVerificationToken> findByToken(String token);
    PhoneVerificationToken findByUser(Users user);
}
