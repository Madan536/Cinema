package com.Spring.Cinema.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.aspectj.bridge.MessageUtil;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String UserName;
    private String UserImage;
    private String Email;
    private String password;
    private String Roles;

    public Users(String userName, String email, String password ,String UserImage) {
       this.UserName = userName;
        this.Email = email;
        this.password = password;
        this.UserImage=UserImage;
        this.Roles="User";
    }
}
