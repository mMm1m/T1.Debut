package org.example.task_1.model;

import javax.persistence.*;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "users_table")
public class User {
    public User(String lastName, String firstName, String email, String role
            , String code, String token, String status){
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.role = role;
        this.code = code;
        this.token = token;
        this.status = status;

    }
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "role")
    private String role;
    @Column(name = "code")
    private String code;
    @Column(name = "token")
    private String token;
    @Column(name = "status")
    private String status;
}
