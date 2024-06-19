package org.example.task_1.openFeignClient;

import org.example.task_1.configuration.FeignOkHttpConfig;
import org.example.task_1.requestRecords.TokenRequest;
import org.example.task_1.requestRecords.UserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "address-service", url = "http://193.19.100.32:7000", configuration = FeignOkHttpConfig.class)
public interface T1Client {
    @GetMapping("/api/get-roles")
    public ResponseEntity<String> getRoles();
    @GetMapping("/api/get-code")
    public ResponseEntity<String> getCode(@RequestParam(name = "email") String email);
    @PostMapping("/api/set-status")
    public ResponseEntity<String> setStatus(@RequestBody TokenRequest token);
    @PostMapping("/api/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserRequest user);
}