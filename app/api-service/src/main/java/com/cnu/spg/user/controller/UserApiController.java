package com.cnu.spg.user.controller;

import com.cnu.spg.config.resolver.UserId;
import com.cnu.spg.user.domain.User;
import com.cnu.spg.user.dto.UserPasswordChangingDto;
import com.cnu.spg.user.dto.UserRegisterDto;
import com.cnu.spg.user.dto.requset.PasswordConfirmRequestDto;
import com.cnu.spg.user.dto.response.UserInfoResponseDto;
import com.cnu.spg.user.exception.PasswordNotConfirmException;
import com.cnu.spg.user.exception.TokenIsNotValidException;
import com.cnu.spg.user.service.UserService;
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

    @ApiOperation("회원가입 요청")
    @PostMapping("/api/v1/users")
    public ResponseEntity<URI> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {

        URI createUri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(userService.regiesterUser(userRegisterDto))
                .toUri();

        return ResponseEntity.created(createUri).build();
    }

    @ApiOperation("비밀 번호 변경")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @PutMapping("/api/v1/users/{userId}/password")
    public ResponseEntity<Void> changePassword(@PathVariable("userId") Long userId,
                                               @RequestBody @Valid UserPasswordChangingDto userPasswordChangingDto,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) throw new BadCredentialsException(PASSWORD_NOT_MATCHED_MSG);

        userService.changeUserPassword(userId, userPasswordChangingDto);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation("[권한] user 비밀번호 확인")
    @PostMapping("/api/v1/users/{userId}/password")
    public ResponseEntity<Void> checkUserPasswordConfirm(@UserId User user, @PathVariable("userId") Long userId,
                                                         @Valid @RequestBody PasswordConfirmRequestDto passwordConfirmRequestDto) {
        if (user.getId().equals(userId)) {
            if (userService.checkNowPassword(user, passwordConfirmRequestDto.getPassword())) {
                return ResponseEntity.noContent().build();
            }

            throw new PasswordNotConfirmException();
        }
        throw new TokenIsNotValidException();
    }

    @ApiOperation("회원 정보 조회")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @GetMapping("/api/v1/users/{userId}")
    public ResponseEntity<UserInfoResponseDto> getMyProfile(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.searchUserInfo(userId));
    }
}
