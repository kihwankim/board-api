package com.cnu.spg.user.dto.requset;

import com.cnu.spg.user.domain.validation.FieldMatch;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
@Data
public class UserPasswordChangingDto {
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String beforePassword;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String password;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String matchingPassword;

    public UserPasswordChangingDto() {
    }

    @Builder
    public UserPasswordChangingDto(String beforePassword, String password, String matchingPassword) {
        this.beforePassword = beforePassword;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }
}
