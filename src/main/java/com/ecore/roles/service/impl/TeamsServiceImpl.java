package com.ecore.roles.service.impl;

import com.ecore.roles.client.TeamsClient;
import com.ecore.roles.client.model.Team;
import com.ecore.roles.exception.InvalidArgumentException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.service.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

@Service
public class TeamsServiceImpl implements TeamsService {

    private final TeamsClient teamsClient;

    @Autowired
    public TeamsServiceImpl(TeamsClient teamsClient) {
        this.teamsClient = teamsClient;
    }

    public Team getTeam(UUID id) {
        return teamsClient.getTeam(id).getBody();
    }

    public List<Team> getTeams() {
        return teamsClient.getTeams().getBody();
    }

    @Override
    public boolean isUserPartOfTeam(@NotNull UUID userId, @NotNull UUID teamId) throws ResourceNotFoundException {
        if (userId == null || teamId == null) {
            throw new IllegalArgumentException();
        }
        Team team = Optional.ofNullable(getTeam(teamId)).orElseThrow(() -> new ResourceNotFoundException(Team.class, teamId));
        return team.getTeamLeadId() == userId || team.getTeamMemberIds().contains(userId);
    }
}
