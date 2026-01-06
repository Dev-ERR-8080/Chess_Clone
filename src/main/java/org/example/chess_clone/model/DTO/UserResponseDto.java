package org.example.chess_clone.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public final class UserResponseDto {

    Long userId;
    String username;
    String email;
    String role;
    String pfpUrl;
    String name;
    String country;


}
