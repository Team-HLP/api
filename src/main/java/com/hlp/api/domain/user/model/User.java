package com.hlp.api.domain.user.model;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.hlp.api.common.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 50)
    @Column(name = "login_id", nullable = false)
    private String loginId;

    @NotNull
    @Column(name = "password", nullable = false, columnDefinition = "MEDIUMTEXT")
    private String password;

    @NotNull
    @Size(max = 20)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull
    @Enumerated(value = STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @NotNull
    @Size(max = 20)
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotNull
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Builder
    private User(
        Integer id,
        String loginId,
        String password,
        String name,
        Integer age,
        Sex sex,
        String phoneNumber,
        Boolean isDeleted
    ) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.isDeleted = isDeleted;
    }
}
