package com.ecore.roles.service;

import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.model.Role;

import java.util.List;
import java.util.UUID;

public interface RolesService {

    Role createRole(Role role);

    Role getRole(UUID id);

    List<Role> getRoles();

    Role searchRole(UUID userId, UUID teamId) throws ResourceNotFoundException;

}
