package com.cnu.spg.api.user.controller;

import com.cnu.spg.user.dto.UserPasswordChangingDto;
import com.cnu.spg.user.dto.UserRegisterDto;
import com.cnu.spg.user.dto.response.UserInfoResponseDto;
import com.cnu.spg.user.exception.PasswordConfirmException;
import com.cnu.spg.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/user-service/v1")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("/health-check")
    public String checkAlive() {
        return "alive";
    }

    @PostMapping("/users")
    public ResponseEntity<URI> register(@RequestBody UserRegisterDto userRegisterDto) {

        URI createUri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(userService.regiesterUser(userRegisterDto))
                .toUri();

        return ResponseEntity.created(createUri).build();
    }

    @PutMapping("/users/{userId}/password")
    public ResponseEntity<Void> changePassword(@PathVariable("userId") Long userId,
                                               @RequestBody @Valid UserPasswordChangingDto userPasswordChangingDto,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            throw new PasswordConfirmException("password is not matched");

        userService.changeUserPassword(userId, userPasswordChangingDto);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserInfoResponseDto> getMyProfile(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.searchUserInfo(userId));
    }
}
