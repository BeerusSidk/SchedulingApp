package cs309.backend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangePasswordData (
    @NotNull @NotBlank String currentPassword,

    @NotNull @NotBlank String newPassword,

    @NotNull @NotBlank String confirmationPassword
){}
