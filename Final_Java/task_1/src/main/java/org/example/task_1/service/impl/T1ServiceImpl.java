package org.example.task_1.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jdk.swing.interop.SwingInterOpUtils;
import org.example.task_1.enums.ConstStrings;
import org.example.task_1.model.User;
import org.example.task_1.openFeignClient.T1Client;
import org.example.task_1.repository.UserRepository;
import org.example.task_1.requestRecords.TokenRequest;
import org.example.task_1.requestRecords.UserRequest;
import org.example.task_1.service.T1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class T1ServiceImpl {
    @Autowired
    private T1Client client;
    @Autowired
    private UserRepository userRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    private String encodeBASE64String(String startCondition){
        return Base64.getEncoder().encodeToString(startCondition.getBytes(StandardCharsets.US_ASCII));
    }

    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(ConstStrings.EMAIL_REGEX.getCode());
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public ResponseEntity<String> getRoles(){
        ResponseEntity<String> roles = client.getRoles();
        if(!ConstStrings.STRING_OF_ROLES.getCode().equals(roles.getBody())) return new ResponseEntity<>("Некорректный список ролей", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(roles.getBody(), HttpStatus.OK);
    }

    public ResponseEntity<String> getCode(@RequestParam(name = "email") String email){
        ResponseEntity<String> client_code;
        String decodeCode;
        try {
            client_code = client.getCode(email);
            StringBuilder builder = new StringBuilder();
            builder.append(email);
            builder.append(":");
            builder.append(client_code.getBody());
            decodeCode = encodeBASE64String(builder.toString());
            User user = userRepository.findByEmail(email).get();
            userRepository.deleteById(user.getId());
            user.setCode(client_code.getBody()); user.setToken(decodeCode);
            userRepository.save(user);
        }
        catch (FeignException feignException) {
            return new ResponseEntity<>("Некорректно введенная почта", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok()
                .header("Content-Type", "text/plain","charset=US_ASCII")
                .body(client_code.getBody()+"\n"+decodeCode+" - "+"код для установки статуса");
    }

    public ResponseEntity<String> setStatus(@RequestBody TokenRequest token){
        try {
            if(!token.status().equals("increased")) return new ResponseEntity<>("Неожиданный статус", HttpStatus.BAD_REQUEST);
            Optional<User> user = userRepository.findByToken(token.token());
            if(user.isPresent()){
                client.setStatus(token);
                if(user.get().getStatus().isEmpty()) {
                    userRepository.deleteById(user.get().getId());
                    user.get().setStatus("increased");
                    userRepository.save(user.get());
                    return new ResponseEntity<>("Установлен статус increased", HttpStatus.OK);
                }
                else return new ResponseEntity<>("Выполнен статус increased. Задание выполнено", HttpStatus.OK);
            }
            else return new ResponseEntity<>("Некорректный токен", HttpStatus.BAD_REQUEST);
        }
        catch (Error error)
        {
            return new ResponseEntity<>("Некорректные входные данные", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> signUp(@RequestBody UserRequest user) throws JsonProcessingException {
        // URL для отправки запроса
        RestTemplate restTemplate = new RestTemplate();
        String url  = "http://193.19.100.32:7000/api/sign-up";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        System.out.println(user.toString());
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        Set<String> alreadyVisited = new HashSet<>();
        // пусть максимум может быть 10 перенаправлений
        while(response.getStatusCode().is3xxRedirection()){
            String redirectUrl = response.getHeaders().getLocation().toString();
            if(alreadyVisited.contains(redirectUrl) || alreadyVisited.size() > 10)
                return new ResponseEntity<>("Взаимная блокировка", HttpStatus.TEMPORARY_REDIRECT);
            alreadyVisited.add(redirectUrl);
            response = restTemplate.exchange(redirectUrl, HttpMethod.POST, entity, String.class);
        }
        if(!isValidEmail(user.email())) return new ResponseEntity<>("Некорректная почта пользователя", HttpStatus.BAD_REQUEST);
        boolean flag = false;
        for(var a : ConstStrings.values())
        {
            if(a.getCode().equals(user.role())){
                flag = true;
                break;
            }
        }
        if(!flag) return new ResponseEntity<>("Некорректная введенная роль", HttpStatus.BAD_REQUEST);
        var all = userRepository.findAll();
        for(var a : all) {
            if(a.getEmail().equals(user.email())) return new ResponseEntity<>("Пользователь с такой почтой уже есть", HttpStatus.BAD_REQUEST);
        }
        userRepository.save(new User(user.last_name(), user.first_name(), user.email(), user.role(), "", "", ""));
        return new ResponseEntity<>("Данные внесены", response.getStatusCode());
    }
}
