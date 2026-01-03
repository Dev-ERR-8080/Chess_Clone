package org.example.chess_clone.model.DTO;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LoginDto {
    String userEmailId;
    String password;
}
