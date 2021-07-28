package com.cnu.spg.user.service;

import com.cnu.spg.user.domain.Role;
import com.cnu.spg.user.domain.RoleName;
import com.cnu.spg.user.domain.User;
import com.cnu.spg.user.dto.UserPasswordChangingDto;
import com.cnu.spg.user.dto.UserRegisterDto;
import com.cnu.spg.user.dto.response.UserInfoResponseDto;
import com.cnu.spg.user.exception.PasswordNotMatchException;
import com.cnu.spg.user.exception.ResourceNotFoundException;
import com.cnu.spg.user.exception.UsernameAlreadyExistException;
import com.cnu.spg.user.repository.RoleRepository;
import com.cnu.spg.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long regiesterUser(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByUsername(userRegisterDto.getUserName())) {
            throw new UsernameAlreadyExistException(userRegisterDto.getUserName());
        }

        Role unAuthUserRole = roleRepository.findByName(RoleName.ROLE_UNAUTH)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "roleName", RoleName.ROLE_UNAUTH));

        String encrytPassword = passwordEncoder.encode(userRegisterDto.getMatchingPassword());
        User user = User.createUser(userRegisterDto.getName(), userRegisterDto.getUserName(), encrytPassword, unAuthUserRole);

        return userRepository.save(user).getId();
    }

    public User findByUserName(String userName) {
        return this.userRepository.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user name", userName));
    }

    @Transactional
    public void deleteByUserName(String username) {
        this.userRepository.deleteByUsername(username);
    }

    @Transactional
    public User changeUserPassword(String username, UserPasswordChangingDto userPasswordChangingDto) {
        User oridinaryUser = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        boolean isUserPasswordCorrect = this.passwordEncoder.matches(userPasswordChangingDto.getBeforePassword()
                , oridinaryUser.getPassword());
        if (!isUserPasswordCorrect) {
            return null;
        }
        oridinaryUser.changePassword(this.passwordEncoder.encode(userPasswordChangingDto.getPassword()));

        return oridinaryUser;
    }

    @Transactional
    public void changeUserPassword(Long userId, UserPasswordChangingDto userPasswordChangingDto) {
        User oridinaryUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        if (!passwordEncoder.matches(userPasswordChangingDto.getBeforePassword()
                , oridinaryUser.getPassword())) {
            throw new PasswordNotMatchException();
        }

        oridinaryUser.changePassword(this.passwordEncoder.encode(userPasswordChangingDto.getPassword()));
    }

    public UserInfoResponseDto searchUserInfo(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, userId));

        return new UserInfoResponseDto(findUser);
    }

    public boolean checkNowPassword(String username, String passowrd) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return this.passwordEncoder.matches(passowrd, user.getPassword());
    }

    @Transactional
    public User updateUsernameAndName(String pastUserName, String username, String name) {
        User user = this.userRepository.findByUsername(pastUserName)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        user.changeName(name);

        return user;
    }
}
