package com.hlp.api.domain.guardian.model;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.hlp.api.common.model.BaseEntity;
import com.hlp.api.domain.user.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "guardian_children_map")
@NoArgsConstructor(access = PROTECTED)
public class GuardianChildrenMap extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "guardian_id", nullable = false)
    private Guardian guardian;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "children_id", nullable = false)
    private User children;

    @Builder
    private GuardianChildrenMap(
        Integer id,
        Guardian guardian,
        User children
    ) {
        this.id = id;
        this.guardian = guardian;
        this.children = children;
    }
}
