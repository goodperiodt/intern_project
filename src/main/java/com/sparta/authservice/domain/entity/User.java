package com.sparta.authservice.domain.entity;

import com.sparta.authservice.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    private User (String email,
                  String password,
                  String nickname) {
    /*
        @Builder 빌더 패턴일 경우 아래와 같이 작성 가능
        그리고 Builder 빌더 패턴은 기본으로 제한자가 public static 이므로 외부에서 접근이 가능하기에
        @Builder(access=AccessLevel.PRIVATE) 하고 이 빌더 패턴을 정적 팩토리 메서드 내부에서 작동하게 하면 됨.
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    */
     this.email = email;
     this.password = password;
     this.role = Role.USER;
     this.nickname = nickname;
    }

    public static User create(String email,
                              String password,
                              String nickname) {
        return new User(email, password, nickname);
    }

    public void changeRoleToAdmin() {
        this.role = Role.ADMIN;
    }
}