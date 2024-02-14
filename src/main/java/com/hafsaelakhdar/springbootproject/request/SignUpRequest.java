package com.hafsaelakhdar.springbootproject.request;

import com.hafsaelakhdar.springbootproject.customvalidations.PasswordMatches;
import com.hafsaelakhdar.springbootproject.customvalidations.ValidEmail;
import com.hafsaelakhdar.springbootproject.customvalidations.ValidPasswordCharacters;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class SignUpRequest {
    @NotNull
    @NotBlank(message = "fullname shouldn't be empty")
    private String username;

    @ValidPasswordCharacters
    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @NotBlank(message = "email shouldn't be empty")
    private String email;

}
