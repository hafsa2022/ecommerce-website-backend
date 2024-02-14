package com.hafsaelakhdar.springbootproject.request;

import com.hafsaelakhdar.springbootproject.customvalidations.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {

    @ValidEmail
    @NotNull
    @NotBlank(message = "email shouldn't be empty")
    private String email;

    @NotNull
    @NotBlank(message = "password shouldn't be empty")
    private String password;
}