package com.sparta.authservice.domain.entity;

import com.sparta.authservice.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String nickname;

    @Builder
    private User (String email,
                  String password,
                  String nickname) {
     this.email = email;
     this.password = password;
     this.role = Role.USER;
     this.nickname = nickname;
    }

    public static User create(String email,
                              String password,
                              String nickname) {
        return new User(email,
                        password,
                        nickname
        );
    }

    public void changeRoleToAdmin() {
        this.role = Role.ADMIN;
    }
}