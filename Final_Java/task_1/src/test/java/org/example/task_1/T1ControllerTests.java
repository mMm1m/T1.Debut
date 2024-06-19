package org.example.task_1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.task_1.requestRecords.JSONResponse;
import org.example.task_1.requestRecords.TokenRequest;
import org.example.task_1.requestRecords.UserRequest;
import org.example.task_1.service.impl.T1ServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class T1ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private T1ServiceImpl t1Service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetRoles() throws Exception {
        JSONResponse jsonResponse = new JSONResponse(List.of("Системный аналитик","Разработчик Java","Разработчик JS/React","Тестировщик","Прикладной администратор"));
        when(t1Service.getRoles()).thenReturn(ResponseEntity.ok(objectMapper.writeValueAsString(jsonResponse)));

        mockMvc.perform(get("/localhost_API/get-roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles").isArray())
                .andExpect(jsonPath("$.roles[0]").value("Системный аналитик"));
    }

    @Test
    public void testGetCode() throws Exception {
        String email = "md-golchanskiy-3@example.com";
        String body = "62e35e92cbbc4919dfe6c4d2a2af8e13";
        when(t1Service.getCode(email)).thenReturn(ResponseEntity.ok(body));

        mockMvc.perform(get("/localhost_API/get-code")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string(body));
    }

    @Test
    public void testSetStatus() throws Exception {
        TokenRequest tokenRequest = new TokenRequest("bWQtZ29sY2hhbnNraXktMUBleGFtcGxlLmNvbTo1YzY0NzE0NDlmNjIyMzMyZTEwNGQ1ZWY2ZTVmYmMzZA==", "increased");
        when(t1Service.setStatus(tokenRequest)).thenReturn(ResponseEntity.ok("Установлен статус increased"));

        mockMvc.perform(post("/localhost_API/set-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tokenRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Установлен статус increased"));
    }

    @Test
    public void testSignUp() throws Exception {
        UserRequest userRequest = new UserRequest("Golchanskiy", "Maxim", "md-golchanskiy-4@example.ru", "Системный аналитик");
        when(t1Service.signUp(userRequest)).thenReturn(ResponseEntity.ok("Данные внесены"));

        mockMvc.perform(post("/localhost_API/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Данные внесены"));
    }
}
