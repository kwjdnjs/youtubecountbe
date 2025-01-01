package com.example.youtubecount.entity;

import com.example.youtubecount.enumType.Role;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Entity
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 50, nullable = false, unique = true)
    private String contact;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private AuthEntity auth;

    @Builder
    public UserEntity(String email, String contact, String username, String password, Role role) {
        this.role = role;
        this.email = email;
        this.contact = contact;
        this.username = username;
        this.password = password;
    }
}
