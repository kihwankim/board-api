package com.cnu.spg.user.domain;

import com.cnu.spg.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 30, nullable = false)
    private String username;

    @Column(nullable = false, length = 70)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        roles.add(role);
    }

    protected User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public void changePassword(String newEncryptPassword) {
        this.password = newEncryptPassword;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public static User createUser(String name, String username, String password, Role... roles) {
        User user = new User(name, username, password);
        Arrays.stream(roles).forEach(user::addRole);

        return user;
    }
}
