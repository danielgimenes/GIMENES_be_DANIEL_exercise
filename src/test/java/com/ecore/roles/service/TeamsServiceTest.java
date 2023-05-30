package com.ecore.roles.service;

import com.ecore.roles.client.TeamsClient;
import com.ecore.roles.client.model.Team;
import com.ecore.roles.service.impl.TeamsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static com.ecore.roles.utils.TestData.ORDINARY_CORAL_LYNX_TEAM;
import static com.ecore.roles.utils.TestData.ORDINARY_CORAL_LYNX_TEAM_UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamsServiceTest {

    @InjectMocks
    private TeamsServiceImpl TeamsService;
    @Mock
    private TeamsClient TeamsClient;

    @Test
    void shouldGetTeamWhenTeamIdExists() {
        Team ordinaryCoralLynxTeam = ORDINARY_CORAL_LYNX_TEAM();
        when(TeamsClient.getTeam(ORDINARY_CORAL_LYNX_TEAM_UUID))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ordinaryCoralLynxTeam));
        assertNotNull(TeamsService.getTeam(ORDINARY_CORAL_LYNX_TEAM_UUID));
    }

    @Test
    void shouldCheckIfUserIsPartOfTeamWhenUserIsAMember() {
        Team team = ORDINARY_CORAL_LYNX_TEAM();
        UUID userId = team.getTeamMemberIds().get(0);

        when(TeamsClient.getTeam(team.getId()))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(team));

        assertTrue(TeamsService.isUserPartOfTeam(userId, team.getId()));
    }

    @Test
    void shouldCheckIfUserIsPartOfTeamWhenUserIsTheTeamLead() {
        Team team = ORDINARY_CORAL_LYNX_TEAM();
        UUID userId = team.getTeamLeadId();

        when(TeamsClient.getTeam(team.getId()))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(team));

        assertTrue(TeamsService.isUserPartOfTeam(userId, team.getId()));
    }

    @Test
    void shouldCheckIfUserIsPartOfTeamWhenTeamIdIsNull() {
        Team team = ORDINARY_CORAL_LYNX_TEAM();
        UUID userId = team.getTeamMemberIds().get(0);

        assertThrows(IllegalArgumentException.class,
                () -> TeamsService.isUserPartOfTeam(userId, null));
    }

    @Test
    void shouldCheckIfUserIsPartOfTeamWhenUserIdIsNull() {
        Team team = ORDINARY_CORAL_LYNX_TEAM();

        assertThrows(IllegalArgumentException.class,
                () -> TeamsService.isUserPartOfTeam(null, team.getId()));
    }

}
