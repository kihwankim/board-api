package com.cnu.spg.user.domain;

import com.cnu.spg.board.domain.Board;
import com.cnu.spg.board.domain.Comment;
import com.cnu.spg.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @NaturalId
    @Size(max = 10)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(max = 70)
    private String password;

    private String name;

    private Calendar activeDate;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    public void addRole(Role role) {
        roles.add(role);
    }

    protected User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public static User createUser(String name, String username, String password, Role... roles) {
        User user = new User(name, username, password);
        Arrays.stream(roles).forEach(user::addRole);

        return user;
    }
}
