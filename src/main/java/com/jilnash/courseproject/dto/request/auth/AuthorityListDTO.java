package com.jilnash.courseproject.dto.request.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class AuthorityListDTO {

    @NotNull(message = "Authorities list is required")
    @NotEmpty(message = "Authorities list should not be empty")
    private List<
            @Valid @Pattern(regexp = "^(ADMIN|STUDENT|TEACHER)$", message = "Invalid authority name") String
            > authorities;
}
