package biz.advanceitgroup.taxirideserver.authentification.repositories;

import biz.advanceitgroup.taxirideserver.authentification.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

	Optional<Roles> findByRole(String role);
        
        @Modifying
        @Transactional
  	@Query(value ="UPDATE `users_roles` SET`role_id`=?2 WHERE `user_id`=?1", nativeQuery = true)
	void updateUsersRole(long userId,long roleId);
}
