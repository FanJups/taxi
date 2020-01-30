package biz.advanceitgroup.taxirideserver.authentification.services.interfaces;

import biz.advanceitgroup.taxirideserver.authentification.entities.Roles;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RoleService {

    Roles save(Roles role);

    Collection<Roles> findAll();

    Roles createRole(Roles role) ;

    void updateRole(Long userId, long roleId) ;

    void deleteRole(Long roleId) ;

    List<Roles> getRoleByUserId(Long userId) ;

    Roles addRoleToUser(Long userId, Roles role) ;

    Roles updateRoleWithUser(Long userId, Long roleId, Roles roleRequest) ;

    void deleteRoleWithUser(Long userId, Long roleId) ;

    Optional<Roles> findById(Long roleId);

    Optional<Roles> findByRole(String role);
}
