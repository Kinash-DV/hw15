package dv.kinash.hw15.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserNewDTO {
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private String password;
}
