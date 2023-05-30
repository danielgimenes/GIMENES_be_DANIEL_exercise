package com.ecore.roles.service;

import com.ecore.roles.client.model.Team;
import com.ecore.roles.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

public interface TeamsService {

    Team getTeam(UUID id);

    List<Team> getTeams();

    boolean isUserPartOfTeam(UUID userId, UUID teamId) throws ResourceNotFoundException;
}
