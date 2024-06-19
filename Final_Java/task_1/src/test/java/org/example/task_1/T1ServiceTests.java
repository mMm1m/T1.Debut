package org.example.task_1;

import org.example.task_1.model.User;
import org.example.task_1.openFeignClient.T1Client;
import org.example.task_1.repository.UserRepository;
import org.example.task_1.requestRecords.TokenRequest;
import org.example.task_1.requestRecords.UserRequest;
import org.example.task_1.service.impl.T1ServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class T1ServiceTests {

    @InjectMocks
    private T1ServiceImpl t1Service;

    @Mock
    private T1Client t1Client;

    @Mock
    private UserRepository usersRepository;

    @Test
    public void testGetRoles() {
        String rolesJson = "{\"roles\":[\"Системный аналитик\",\"Разработчик Java\"]}";
        when(t1Client.getRoles()).thenReturn(ResponseEntity.ok(rolesJson));

        ResponseEntity<String> response = t1Service.getRoles();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(rolesJson, response.getBody());
    }

    @Test
    public void testGetCode() {
        String email = "md-golchanskiy-1@example.com";
        String code = "5c6471449f622332e104d5ef6e5fbc3d";
        String encodedCode = "bWQtZ29sY2hhbnNraXktMUBleGFtcGxlLmNvbTo1YzY0NzE0NDlmNjIyMzMyZTEwNGQ1ZWY2ZTVmYmMzZA==";
        User user = new User("Golchanskiy", "Maxim", email, "Разработчик Java", "", "", "");

        when(t1Client.getCode(email)).thenReturn(ResponseEntity.ok(code));
        when(usersRepository.findByEmail(email)).thenReturn(Optional.of(user));

        ResponseEntity<String> response = t1Service.getCode(email);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains(code));
        assertTrue(response.getBody().contains(encodedCode));
    }

    @Test
    public void testSetStatus() {
        TokenRequest tokenRequest = new TokenRequest("bWQtZ29sY2hhbnNraXktMUBleGFtcGxlLmNvbTo1YzY0NzE0NDlmNjIyMzMyZTEwNGQ1ZWY2ZTVmYmMzZA==", "increased");
        User user = new User("Golchanskiy", "Maxim", "md-golchanskiy-1@example.com", "Разработчик Java",
                "5c6471449f622332e104d5ef6e5fbc3d", "bWQtZ29sY2hhbnNraXktMUBleGFtcGxlLmNvbTo1YzY0NzE0NDlmNjIyMzMyZTEwNGQ1ZWY2ZTVmYmMzZA==", "");

        when(usersRepository.findByToken("bWQtZ29sY2hhbnNraXktMUBleGFtcGxlLmNvbTo1YzY0NzE0NDlmNjIyMzMyZTEwNGQ1ZWY2ZTVmYmMzZA==")).thenReturn(Optional.of(user));

        ResponseEntity<String> response = t1Service.setStatus(tokenRequest);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Установлен статус increased", response.getBody());
    }

    @Test
    public void testSignUp() {
        UserRequest userRequest = new UserRequest("Golchanskiy", "Maxim", "md-golchanskiy-2@example.com", "Системный аналитик");
        when(usersRepository.findByEmail(userRequest.email())).thenReturn(Optional.empty());

        ResponseEntity<String> response = t1Service.signUp(userRequest);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Данные внесены", response.getBody());
    }
}
