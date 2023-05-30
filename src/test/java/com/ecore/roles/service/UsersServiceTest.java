package com.ecore.roles.service;

import com.ecore.roles.client.UsersClient;
import com.ecore.roles.client.model.User;
import com.ecore.roles.service.impl.UsersServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.ecore.roles.utils.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @InjectMocks
    private UsersServiceImpl usersService;
    @Mock
    private UsersClient usersClient;

    @Test
    void shouldGetUserWhenUserIdExists() {
        User gianniUser = GIANNI_USER();
        when(usersClient.getUser(UUID_1))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body(gianniUser));

        assertNotNull(usersService.getUser(UUID_1));
    }

    @Test
    void shouldFailToGetUserWhenUserDoesNotExist() {
        when(usersClient.getUser(UUID_1))
                .thenReturn(ResponseEntity.notFound().build());

        assertNull(usersService.getUser(UUID_1));
    }

    @Test
    void shouldGetUsers() {
        List<User> users = List.of(GIANNI_USER(), SECOND_USER());
        when(usersClient.getUsers())
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body(users));

        assertEquals(users, usersService.getUsers());
    }

    @Test
    void shouldGetUsersWhenThereAreNone() {
        when(usersClient.getUsers())
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body(null));

        assertNull(usersService.getUsers());
    }
}
