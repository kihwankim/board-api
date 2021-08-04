package com.cnu.spg.user.dto;

import com.cnu.spg.user.domain.validation.FieldMatch;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
@Getter
@Setter
public class UserRegisterDto {
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String userName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String password;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String matchingPassword;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String name;

    public UserRegisterDto() {
    }

    @Builder
    public UserRegisterDto(String userName, String password, String matchingPassword, String name) {
        this.userName = userName;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.name = name;
    }
}
