package org.example.chess_clone.model.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data

public class RegisterDto {
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 4, max = 50)
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String userEmailId;

    @NotNull(message = "name cant be empty")
    @Size(min = 4, max=50)
    private String userFullName;

    private String pfpUrl;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;


}
