package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "username")
    @NotBlank(message = "Username is mandatory")
    private String username;
    @Column(name = "password")
    @NotBlank(message = "Password is mandatory")
    private String password;
    @Column(name = "fullname")
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @Column(name = "role")
    @NotBlank(message = "Role is mandatory")
    private String role;
}
