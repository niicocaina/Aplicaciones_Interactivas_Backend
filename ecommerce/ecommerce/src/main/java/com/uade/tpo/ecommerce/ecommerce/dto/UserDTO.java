package com.uade.tpo.ecommerce.ecommerce.dto;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private Date birthday;

    public UserDTO() {}

    public UserDTO(User user) {
     this.id = user.getUserId();
     this.name = user.getName();
     this.lastName = user.getLastName();
     this.userName = user.getUserName();
     this.email = user.getEmail();
     this.password = user.getPassword();
     this.birthday = user.getBirthday();
    }
}
