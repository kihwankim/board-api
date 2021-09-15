package com.cnu.spg.user.controller;

import com.cnu.spg.user.service.UserService;
import com.cnu.spg.user.dto.UserPasswordChangingDto;
import com.cnu.spg.user.dto.UserRegisterDto;
import com.cnu.spg.user.dto.response.UserInfoResponseDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private static final String PASSWORD_NOT_MATCHED_MSG = "password is not matched";

    private final UserService userService;

    @ApiOperation("health check용도로 사용")
    @GetMapping("/api/user-service/v1/health-check")
    public String checkAlive() {
        return "alive";
    }

    @ApiOperation("회원가입 요청")
    @PostMapping("/user-service/v1/users")
    public ResponseEntity<URI> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {

        URI createUri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(userService.regiesterUser(userRegisterDto))
                .toUri();

        return ResponseEntity.created(createUri).build();
    }

    @ApiOperation("비밀 번호 변경")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @PutMapping("/api/user-service/v1/users/{userId}/password")
    public ResponseEntity<Void> changePassword(@PathVariable("userId") Long userId,
                                               @RequestBody @Valid UserPasswordChangingDto userPasswordChangingDto,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) throw new BadCredentialsException(PASSWORD_NOT_MATCHED_MSG);

        userService.changeUserPassword(userId, userPasswordChangingDto);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation("회원 정보 조회")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @GetMapping("/user-service/v1/users/{userId}")
    public ResponseEntity<UserInfoResponseDto> getMyProfile(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.searchUserInfo(userId));
    }
}
