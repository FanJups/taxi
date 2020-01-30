package biz.advanceitgroup.taxirideserver.authentification.repositories;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.authentification.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	@Override
	Optional<RefreshToken> findById(Long id);

	Optional<String> findTokenById(Long id);

	Optional<RefreshToken> findByToken(String token);

	Optional<Users> findUserById(Long id);

	Optional<Users> findUserByToken(String token);
}
