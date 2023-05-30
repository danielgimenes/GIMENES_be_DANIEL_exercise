package com.ecore.roles.service.impl;

import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.model.Membership;
import com.ecore.roles.model.Role;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.MembershipsService;
import com.ecore.roles.service.RolesService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class RolesServiceImpl implements RolesService {

    private final RoleRepository roleRepository;

    private final MembershipRepository membershipRepository;

    @Autowired
    public RolesServiceImpl(
            RoleRepository roleRepository,
            MembershipRepository membershipRepository,
            MembershipsService membershipsService) {
        this.roleRepository = roleRepository;
        this.membershipRepository = membershipRepository;
    }

    @Override
    public Role createRole(@NonNull Role r) {
        if (roleRepository.findByName(r.getName()).isPresent()) {
            throw new ResourceExistsException(Role.class);
        }
        return roleRepository.save(r);
    }

    @Override
    public Role getRole(@NonNull UUID roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(Role.class, roleId));
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role searchRole(UUID userId, UUID teamId) throws ResourceNotFoundException {
        return Optional.ofNullable(
                membershipRepository.findByUserIdAndTeamId(userId, teamId)
                .orElseThrow(() -> new ResourceNotFoundException(Membership.class))
                .getRole()
               ).orElseThrow(() -> new ResourceNotFoundException(Role.class));
    }
}
