package biz.advanceitgroup.taxirideserver.authentification.services.impl;

import biz.advanceitgroup.taxirideserver.authentification.entities.Roles;
import biz.advanceitgroup.taxirideserver.authentification.dto.RoleRequest;

import biz.advanceitgroup.taxirideserver.authentification.repositories.RoleRepository;
import biz.advanceitgroup.taxirideserver.authentification.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<Roles> findByRole(String role) {
        return roleRepository.findByRole(role);
    }

    public Roles save(Roles role) {
        roleRepository.save(role);

        return role;
    }

    /**
     * Find all roles from the database
     */
    public Collection<Roles> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Roles createRole(Roles role) {
        return null;
    }

    @Override
    public void updateRole(Long userId, long roleId) {
       roleRepository.updateUsersRole(userId, roleId);
    }

    @Override
    public void deleteRole(Long roleId) {

    }

    @Override
    public List<Roles> getRoleByUserId(Long userId) {
        return null;
    }

    @Override
    public Roles addRoleToUser(Long userId, Roles role) {
        return null;
    }

    @Override
    public Roles updateRoleWithUser(Long userId, Long roleId, Roles roleRequest) {
        return null;
    }

    @Override
    public void deleteRoleWithUser(Long userId, Long roleId) {

    }

    @Override
    public Optional<Roles> findById(Long roleId) {
        return null;
    }


    /**
     * Creates a new role from the role request
     */
    public Roles createRole(RoleRequest roleRequest) {
        Roles newRole = new Roles();
        newRole.setRole(roleRequest.getRole());
        return newRole;
    }
}

