package org.example.task_1.service;

import org.example.task_1.requestRecords.TokenRequest;
import org.example.task_1.requestRecords.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface T1Service {
    public ResponseEntity<String> getRoles();

    public ResponseEntity<String> getCode(@RequestParam(name = "email") String email);

    public ResponseEntity<String> setStatus(@RequestBody TokenRequest token);

    public ResponseEntity<String> signUp(@RequestBody UserRequest user);
}
