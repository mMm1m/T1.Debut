package org.example.task_1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.task_1.requestRecords.JSONResponse;
import org.example.task_1.requestRecords.TokenRequest;
import org.example.task_1.service.impl.T1ServiceImpl;
import org.example.task_1.requestRecords.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/localhost_API")
@Api(tags = "T1 Controller", description = " реализация операций, связанных с T1_API")
public class T1Controller {
    @Autowired
    private T1ServiceImpl employeeService;

    private final ObjectMapper objectMapper;

    public T1Controller(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/get-roles")
    @ApiOperation("Возвращает список доступных для записи ролей")
    public JSONResponse getRoles() throws JsonProcessingException {
        return objectMapper.readValue(employeeService.getRoles().getBody(), JSONResponse.class);
    }

    @GetMapping("/get-code")
    @ApiOperation("Метод возвращает код , полученный по email-у, а также закодированную строку," +
            " которая является токеном при установке статуса")
    public ResponseEntity<String> getCode(@RequestParam(name = "email") String email){
        return employeeService.getCode(email);
    }
    // дописать метод установки токена, получая его из Redis
    @PostMapping("/set-status")
    @ApiOperation("Устанавливает статус по токену и возвращает текстовое сообщение")
    public ResponseEntity<String> setStatus(@RequestBody TokenRequest token){
        return employeeService.setStatus(token);
    }
    @PostMapping("/sign-up")
    @ApiOperation("Регистрирует пользователя, проверяя почту на валидность и несовпадение её с остальными пользователями," +
            " разрешается заполнять только роль из списка ролей")
    public ResponseEntity<String> signUp(@RequestBody UserRequest user){
        return employeeService.signUp(user);
    }
}
