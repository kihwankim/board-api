package com.cnu.spg.security.dto.request;

import com.cnu.spg.util.UserdataValidatorUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    private String username;
    private String password;

    public void setUsername(String username) {
        if (UserdataValidatorUtils.isUsernameValid(username)) {
            this.username = username;
        }
    }

    public void setPassword(String password) {
        if (UserdataValidatorUtils.isPasswordValid(password)) {
            this.password = password;
        }
    }

    public LoginRequestDto(String username, String password) {
        setUsername(username);
        setPassword(password);
    }
}
