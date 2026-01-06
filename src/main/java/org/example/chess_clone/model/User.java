package org.example.chess_clone.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @NotNull(message = "username filed cant be empty")
    @Size(min = 4,max=50,message = "User Name must have chars in between 4 and 50")
    @Column(unique = true)
    String username;

    @NotNull(message = "email filed cant be empty")
    @Column(unique = true)
    String userEmailId;

    @Size(min = 6,max = 256, message = "Password constraints are not valid")
    String password;

    @NotNull(message = "It's not you its us(Internal server error)")
    String role;

    String pfpUrl;

//    LocalDate dob;
    @Column
    private String fullName;


    String country;

    Integer rating;

    private boolean oauthUser;


}
